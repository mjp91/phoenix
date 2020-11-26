package com.mpearsall.hr;

import com.mpearsall.hr.config.jpa.PrimaryJpaConfig;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Entity;
import java.util.EnumSet;

public class GenerateSchema {

  public static void main(String[] args) {
    final MetadataSources metadataSources = new MetadataSources(new StandardServiceRegistryBuilder()
        .applySettings(PrimaryJpaConfig.jpaProperties())
        .build());

    ClassPathScanningCandidateComponentProvider scanner =
        new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));

    for (BeanDefinition candidateComponent : scanner.findCandidateComponents(args[0])) {
      metadataSources.addAnnotatedClassName(candidateComponent.getBeanClassName());
    }

    final Metadata metadata = metadataSources.buildMetadata();

    final SchemaExport schemaExport = new SchemaExport();
    schemaExport.setFormat(true);
    schemaExport.setOutputFile("create.sql");
    schemaExport.setDelimiter(";");
    schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
  }
}
