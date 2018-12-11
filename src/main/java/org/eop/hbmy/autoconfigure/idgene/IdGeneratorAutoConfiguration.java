package org.eop.hbmy.autoconfigure.idgene;

import org.eop.common.idgene.CycleRadixIdGenerator;
import org.eop.common.idgene.IdGenerator;
import org.eop.hbmy.autoconfigure.idgene.IdGeneratorProperties.GeneratorEntry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixinjie
 * @since 2018-09-06
 */
@Configuration
@EnableConfigurationProperties(IdGeneratorProperties.class)
public class IdGeneratorAutoConfiguration {

	private IdGeneratorProperties idGeneratorProperties;
	
	public IdGeneratorAutoConfiguration(IdGeneratorProperties idGeneratorProperties) {
		this.idGeneratorProperties = idGeneratorProperties;
	}
	
	@Bean
	public IdGenerator idGenerator() {
		GeneratorEntry entry = idGeneratorProperties.getGenerators().get("idGenerator");
		return new CycleRadixIdGenerator(entry.getBaseTime(), entry.getNodeNum());
	}
	
	@Bean
	public IdGenerator antherOne() {
		GeneratorEntry entry = idGeneratorProperties.getGenerators().get("antherOne");
		return new CycleRadixIdGenerator(entry.getBaseTime(), entry.getNodeNum());
	}
}
