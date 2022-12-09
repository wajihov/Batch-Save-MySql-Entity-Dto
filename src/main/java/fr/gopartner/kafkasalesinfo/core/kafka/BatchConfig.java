package fr.gopartner.kafkasalesinfo.core.kafka;


import fr.gopartner.kafkasalesinfo.domain.salesInfo.SalesInfo;
import fr.gopartner.kafkasalesinfo.domain.salesInfo.SalesInfoDto;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SalesInfoItemProcessor salesInfoItemProcessor;
    private final SalesItemWriter salesItemWriter;
    private final JobLauncher jobLauncher;
    @Value("${inputFile}")
    private String url;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                       SalesInfoItemProcessor salesInfoItemProcessor, SalesItemWriter salesItemWriter, JobLauncher jobLauncher) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.salesInfoItemProcessor = salesInfoItemProcessor;
        this.salesItemWriter = salesItemWriter;
        this.jobLauncher = jobLauncher;
    }

    @Bean
    public Job importSalesInfo() {
        return jobBuilderFactory.get("importSalesInfo" + java.lang.System.currentTimeMillis())
                .incrementer(new RunIdIncrementer())
                .start(fromFileIntoDataBase())
                .build();
    }

    @Bean
    public Step fromFileIntoDataBase() {
        return stepBuilderFactory.get("fromFileIntoDatabase")
                .<SalesInfoDto, SalesInfo>chunk(10)
                .reader(salesInfoFileReader())
                .processor(salesInfoItemProcessor)
                .writer(salesItemWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<SalesInfoDto> salesInfoFileReader() {
        return new FlatFileItemReaderBuilder<SalesInfoDto>()
                .resource(new ClassPathResource(url))
                .name("salesInfoFileReader")
                .delimited()
                .delimiter(",")
                .names(new String[]{"product", "seller", "sellerId", "price", "city", "category"})
                .linesToSkip(1)
                .targetType(SalesInfoDto.class)
                .build();
    }

    @Bean
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        var parameters = new JobParameters();
        var jobExecution = jobLauncher.run(importSalesInfo(), parameters);
        return jobExecution.getStatus();
    }

}
