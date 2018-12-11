package org.eop.hbmy.autoconfigure.idgene;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lixinjie
 * @since 2018-09-06
 */
@ConfigurationProperties(prefix = "idgenerator")
public class IdGeneratorProperties {

	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	private Map<String, GeneratorEntry> generators;

	public Map<String, GeneratorEntry> getGenerators() {
		return generators;
	}

	public void setGenerators(Map<String, GeneratorEntry> generators) {
		this.generators = generators;
	}

	public static class GeneratorEntry {
		//base-time
		private String baseTime = "2018-01-01 00:00:00.000";
		//node-num
		private long nodeNum = 0;
		
		public long getBaseTime() {
			try {
				return df.parse(baseTime).getTime();
			} catch (ParseException pe) {
				throw new RuntimeException(pe);
			}
		}
		public void setBaseTime(String baseTime) {
			this.baseTime = baseTime;
		}
		public long getNodeNum() {
			return nodeNum;
		}
		public void setNodeNum(long nodeNum) {
			this.nodeNum = nodeNum;
		}
	}
}
