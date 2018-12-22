package org.eop.hbase.table.filter;

import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BigDecimalComparator;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.BitComparator;
import org.apache.hadoop.hbase.filter.ColumnCountGetFilter;
import org.apache.hadoop.hbase.filter.ColumnPaginationFilter;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
import org.apache.hadoop.hbase.filter.ColumnValueFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.FuzzyRowFilter;
import org.apache.hadoop.hbase.filter.InclusiveStopFilter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.LongComparator;
import org.apache.hadoop.hbase.filter.MultiRowRangeFilter;
import org.apache.hadoop.hbase.filter.MultiRowRangeFilter.RowRange;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.NullComparator;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RandomRowFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SkipFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.TimestampsFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

/**
 * @author lixinjie
 * @since 2018-12-22
 */
@Component
public class TableFilter {

	public void filter() {
		//以下过滤器都是过滤值的
		
		//一个有序的过滤器列表，使用and或or来计算内部所有过滤器
		FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		Filter filter1 = null;
		Filter filter2 = null;
		list.addFilter(filter1);
		list.addFilter(filter2);
		//按值过滤，条件匹配时返回整行数据
		byte[] family = null;
		byte[] qualifier = null;
		byte[] value = null;
		SingleColumnValueFilter scvf = new SingleColumnValueFilter(family, qualifier, CompareOperator.EQUAL, value);
		//与上面不同的是，只返回匹配的单元格
		ColumnValueFilter cvf = new ColumnValueFilter(family, qualifier, CompareOperator.GREATER_OR_EQUAL, value);
		//对于简单的和family:qualifier:value相等的查询
		//强烈推荐使用下面的方式而不是上面那两个过滤器
		Scan scan = new Scan();
		scan.addColumn(family, qualifier);
		ValueFilter vf = new ValueFilter(CompareOperator.EQUAL, new BinaryComparator(value));
		//跳过过滤器，只要有一个单元格不通过，整行都被抛弃
		Filter filter = null;
		SkipFilter sf = new SkipFilter(filter);
		//列值比较器，和过滤器连用
		//任何以my开头的值
		RegexStringComparator rsc = new RegexStringComparator("my.");
		//用来检测值中是否包含指定的一个子串（大小写敏感）
		SubstringComparator sc = new SubstringComparator("val");
		//按词典顺序比较二进制数组
		BinaryComparator bc = new BinaryComparator(value);
		//列值和一个前缀字节数组进行比较
		BinaryPrefixComparator bpc = new BinaryPrefixComparator(value);
		//列值和一个Long比较
		LongComparator lc = new LongComparator(0L);
		//在所有的字节上进行二进制位操作，返回结果是否是非零
		BitComparator bic = new BitComparator(value, BitComparator.BitwiseOp.XOR);
		//列值和BigDecimal进行数值比较
		BigDecimalComparator bdc = new BigDecimalComparator(BigDecimal.valueOf(0));
		//
		NullComparator nc = new NullComparator();
		
		//以下过滤器都是过滤key的
		
		//列族过滤器
		//通常最好在Scan中指定列族，而不是用过滤器指定
		FamilyFilter ff = new FamilyFilter(CompareOperator.EQUAL, new BinaryComparator(value));
		//列修饰符过滤器
		QualifierFilter qf = new QualifierFilter(CompareOperator.LESS_OR_EQUAL, new BinaryComparator(value));
		//列修饰符前缀过滤器
		ColumnPrefixFilter cpf = new ColumnPrefixFilter(Bytes.toBytes("abc"));
		//可以指定多个前缀
		MultipleColumnPrefixFilter mcpf = new MultipleColumnPrefixFilter(new byte[][] {Bytes.toBytes("abc"), Bytes.toBytes("xyz")});
		//列修饰符范围过滤器
		ColumnRangeFilter crf = new ColumnRangeFilter(Bytes.toBytes("abc"), true, Bytes.toBytes("xyz"), true);
		//行键过滤器
		//通常更好的方法是为Scan设置startRow/stopRow
		RowFilter rf = new RowFilter(CompareOperator.EQUAL, new BinaryComparator(value));
		//支持多个row key范围
		byte[] startRow = null;
		byte[]stopRow = null;
		MultiRowRangeFilter mrrf = new MultiRowRangeFilter(Arrays.asList(new RowRange(startRow, true, stopRow, true), new RowRange(startRow, false, stopRow, true)));
		
		//只返回指定时间戳/版本的所有单元格
		TimestampsFilter tf = new TimestampsFilter(Arrays.asList(0L, 1L));
		//每一行只返回第一个KV，可以高效地用于计数操作
		FirstKeyOnlyFilter fkof = new FirstKeyOnlyFilter();
		//?
		FuzzyRowFilter frf = null;
		//在指定的行后面停止，并没有一个“RowStopFilter”这样的过滤器，因为
		//Scan允许指定一个stopRow，该过滤器的目的是把stopRow包含进来，类似于[A,Z]闭区间
		byte[] stopRowKey = null;
		InclusiveStopFilter isf = new InclusiveStopFilter(stopRowKey);
		//将只返回key部分，value被重写为空
		KeyOnlyFilter kof = new KeyOnlyFilter();
		//随机地返回一些行
		float chance = 0.5F;//机会值
		RandomRowFilter rrf = new RandomRowFilter(chance);
		//返回一行的前N列，不适用于Scan
		int n = 1;
		ColumnCountGetFilter ccgf = new ColumnCountGetFilter(n);
		//列标页码，基于上面这个过滤器，带有两个参数
		int limit = 1;
		int offset = 10;
		ColumnPaginationFilter cpgf = new ColumnPaginationFilter(limit, offset);
		//限制返回结果的行数，因过滤器是在每个region server上单独执行的
		//所以最后返回给客户端的数据行数无法确定，但对单个的region起到限制和优化
		int pageSize = 10;
		PageFilter pf = new PageFilter(pageSize);
	}
}
