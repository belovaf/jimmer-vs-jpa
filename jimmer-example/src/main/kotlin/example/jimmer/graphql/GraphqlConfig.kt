package example.jimmer.graphql

import io.leangen.graphql.ExtensionProvider
import io.leangen.graphql.GeneratorConfiguration
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder
import io.leangen.graphql.metadata.strategy.query.BeanResolverBuilder
import io.leangen.graphql.metadata.strategy.query.RecordResolverBuilder
import io.leangen.graphql.module.Module
import io.leangen.graphql.spqr.spring.autoconfigure.SpqrProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class GraphqlConfig {
    @Bean
    fun resolverBuilderExtensionProvider(props: SpqrProperties) =
        ExtensionProvider<GeneratorConfiguration, Module> { config, current ->
            listOf(Module { ctx ->
                val basePackages = props.basePackages ?: emptyArray()
                ctx.schemaGenerator
                    .withNestedResolverBuilders(
                        AnnotatedResolverBuilder(),
                        BeanResolverBuilder(*basePackages)
                            .withJavaDeprecationRespected(true)
                            .withFilters({ it.name != "METADATA" && it.name != "Companion" }),
                        RecordResolverBuilder(*basePackages)
                    )
            })
        }
}