package org.eop.hbase.admin.region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.hadoop.hbase.ServerName;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.CompactionState;
import org.apache.hadoop.hbase.client.RegionInfo;
import org.apache.hadoop.hbase.util.Bytes;
import org.eop.hbase.admin.HbaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-25
 */
@Component
public class RegionAdmin {

	@Autowired
	private HbaseAdmin hbaseAdmin;
	
	public void assign(String regionName) throws IOException {
		hbaseAdmin.getAdmin().assign(Bytes.toBytes(regionName));
	}
	
	public boolean balance() throws IOException {
		hbaseAdmin.getAdmin().balance();
		boolean force = true;
		return hbaseAdmin.getAdmin().balance(force);
	}
	
	public boolean balancerSwitch(boolean onOrOff) throws IOException {
		//打开或关闭负载均衡器
		boolean synchronous = true;
		return hbaseAdmin.getAdmin().balancerSwitch(onOrOff, synchronous);
	}
	
	public void compactRegion(String regionName, String cf) throws IOException {
		hbaseAdmin.getAdmin().compactRegion(Bytes.toBytes(regionName));
		//压缩一个列族
		hbaseAdmin.getAdmin().compactRegion(Bytes.toBytes(regionName), Bytes.toBytes(cf));
	}
	
	public void compactRegionServer(String serverName) throws IOException {
		//压缩一个region server上的所有region
		hbaseAdmin.getAdmin().compactRegionServer(ServerName.valueOf(serverName));
	}
	
	public void decommissionRegionServers(String[] serverNames, boolean offload) throws IOException {
		//把region server标记为正式停用，以防止其它region再分配给它
		//offload参数表明这些停用的region server上的region是否卸载
		//如果有多个region server需要停用，同时停用它们能够阻止没有必要的region移动
		//region的卸载是异步进行的
		List<ServerName> servers = new ArrayList<>();
		for (String serverName : serverNames) {
			servers.add(ServerName.valueOf(serverName));
		}
		hbaseAdmin.getAdmin().decommissionRegionServers(servers, offload);
	}
	
	public void flushRegion(String regionName) throws IOException {
		hbaseAdmin.getAdmin().flushRegion(Bytes.toBytes(regionName));
	}
	
	public void flushRegionServer(String serverName) throws IOException {
		//flush region server上的所有region
		hbaseAdmin.getAdmin().flushRegionServer(ServerName.valueOf(serverName));
	}
	
	public CompactionState getCompactionStateForRegion(String regionName) throws IOException {
		//获取region当前的压缩状态
		return hbaseAdmin.getAdmin().getCompactionStateForRegion(Bytes.toBytes(regionName));
	}
	
	public long getLastMajorCompactionTimestampForRegion(String regionName) throws IOException {
		return hbaseAdmin.getAdmin().getLastMajorCompactionTimestampForRegion(Bytes.toBytes(regionName));
	}
	
	public List<RegionInfo> getRegions(ServerName serverName) throws IOException {
		//获得一个region server上的所有在线region
		return hbaseAdmin.getAdmin().getRegions(serverName);
	}
	
	public List<RegionInfo> getRegions(TableName tableName) throws IOException {
		//获取一个表对应的所有region
		return hbaseAdmin.getAdmin().getRegions(tableName);
	}

	public void majorCompactRegion(String regionName, String cf) throws IOException {
		hbaseAdmin.getAdmin().majorCompactRegion(Bytes.toBytes(regionName));
		//压缩指定列族
		hbaseAdmin.getAdmin().majorCompactRegion(Bytes.toBytes(regionName), Bytes.toBytes(cf));
	}
	
	public void majorCompactRegionServer(String serverName) throws IOException {
		//压缩region server上的所有region
		hbaseAdmin.getAdmin().majorCompactRegionServer(ServerName.valueOf(serverName));
	}
	
	public void mergeRegionsAsync(byte[][] nameofRegionsToMerge, boolean forcible) throws IOException {
		//合并region
		Future<Void> future = hbaseAdmin.getAdmin().mergeRegionsAsync(nameofRegionsToMerge, forcible);
		future.isDone();
	}
	
	public void mergeRegionsAsync(byte[] nameOfRegionA, byte[] nameOfRegionB, boolean forcible) throws IOException {
		//合并region
		Future<Void> future = hbaseAdmin.getAdmin().mergeRegionsAsync(nameOfRegionA, nameOfRegionB, forcible);
		future.isDone();
	}
	
	public boolean mergeSwitch(boolean enabled, boolean synchronous) throws IOException {
		return hbaseAdmin.getAdmin().mergeSwitch(enabled, synchronous);
	}
	
	public void move(byte[] encodedRegionName, byte[] destServerName) throws IOException {
		//把一个region移到指定的region server
		hbaseAdmin.getAdmin().move(encodedRegionName, destServerName);
	}
	
	public void recommissionRegionServer(ServerName serverName, List<byte[]> encodedRegionNames) throws IOException {
		//从一个region server上移除停止使用标记，使region可以重新分配给它
		hbaseAdmin.getAdmin().recommissionRegionServer(serverName, encodedRegionNames);
	}
	
	public void splitRegionAsync(byte[] regionName, byte[] splitPoint) throws IOException {
		//分割一个region
		Future<Void> future = hbaseAdmin.getAdmin().splitRegionAsync(regionName, splitPoint);
		future.isDone();
	}
	
	public boolean splitSwitch(boolean enabled, boolean synchronous) throws IOException {
		//打开/关闭分割开关
		return hbaseAdmin.getAdmin().splitSwitch(enabled, synchronous);
	}
	
	public void unassign(byte[] regionName, boolean force) throws IOException {
		hbaseAdmin.getAdmin().unassign(regionName, force);
	}
}
