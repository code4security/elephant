package com.sjhy.platform.biz.deploy.utils;

import com.sjhy.platform.biz.deploy.exception.AdmiralNameIncludeHarmonyException;
import com.sjhy.platform.biz.deploy.exception.AdmiralNameIsNotNullableException;
import com.sjhy.platform.biz.deploy.exception.AdmiralNameIsTooLongException;
import com.sjhy.platform.client.dto.fixed.HarmonyWord;
import com.sjhy.platform.persist.mysql.fixed.HarmonyWordMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ShieldingWordsUtil")
public class ShieldingWordsUtil {
	private int admiralNameMaxLong = 14; // 舰队长名最大字节长度
	@Resource
	private HarmonyWordMapper harmonyWordMapper;
	private Pattern pattern; // 用于校验用户名,正则表达式的编译表示形式。 
	
	/**
	 * 初始化正则表达式
	 * @throws IOException
	 */
	@PostConstruct
	public void start() throws IOException {
		StringBuffer sb = new StringBuffer(10000);
		
		// 从数据库中获取全部敏感词(去除可能造成正则表达式初始化失败的特殊字符)
		List<HarmonyWord> harmonyWordList = harmonyWordMapper.selectByAll();
		for (HarmonyWord harmonyWord : harmonyWordList) {
			if(harmonyWord.getName().indexOf("*") != -1) 
				continue; // 默认过滤所有含 * 的词,因此本敏感词不需要加入列表中
			if(harmonyWord.getName().indexOf("(") != -1)
				continue; // 默认过滤所有含 ( 的词,因此本敏感词不需要加入列表中
			if(harmonyWord.getName().indexOf(")") != -1)
				continue; // 默认过滤所有含 ) 的词,因此本敏感词不需要加入列表中
			if(harmonyWord.getName().indexOf("?") != -1)
				continue; // 默认过滤所有含 ? 的词,因此本敏感词不需要加入列表中
			
			sb.append(harmonyWord.getName()).append("|");
		}
		
		if(sb.length() > 1)
			sb.deleteCharAt(sb.length()-1);

		// 加入需要过滤的特殊字符(为了安全必须过滤)
		sb.append("|").append("[*]").append("|"); // 要用正则,此行必须
		sb.append("[(]").append("|"); // 要用正则,此行必须
		sb.append("[)]").append("|"); // 要用正则,此行必须
		sb.append("[?]").append("|"); // 要用正则,此行必须
		sb.append(" ").append("|"); // 半角空格,此行及以下可选
		sb.append("　").append("|"); // 全角空格
		sb.append("[+]").append("|"); // 要用正则
		sb.append("[||]").append("|");
		sb.append("[&]").append("|");
		sb.append("[!]").append("|");
		sb.append("[{]").append("|");
		sb.append("[}]").append("|");
		sb.append("\\[").append("|");
		sb.append("\\]").append("|");
		sb.append("\\^").append("|");
		sb.append("\"").append("|");
		sb.append("'").append("|");
		sb.append(":").append("|");
		sb.append(";").append("|");
		sb.append(",").append("|");
		sb.append("，").append("|");
		sb.append("、").append("|");
		sb.append("【").append("|");
		sb.append("】").append("|");
		sb.append("\\\\");
		
		pattern = Pattern.compile(sb.toString()); // 校验舰队长名专用
	}

	/**
	 * 舰船舰队长的名字是否符合要求
	 * @paramn str 要格式化的字符串
	 * @return 格式化后的字符串
	 * @throws AdmiralNameIsNotNullableException 舰队长名不允许为空
	 * @throws AdmiralNameIsTooLongException 舰队长名超出允许长度
	 * @throws AdmiralNameIncludeHarmonyException 舰队长名不允许包含特殊字符或被和谐词
	 */
	public void checkAdmiralName(String admiralName) throws AdmiralNameIsNotNullableException, AdmiralNameIsTooLongException, AdmiralNameIncludeHarmonyException {
		// 舰队长名不允许为空
		if (StringUtils.isEmpty(admiralName))
			throw new AdmiralNameIsNotNullableException();
		
		// 舰队长名长度不允许超标
		try {
			int length = admiralName.getBytes("GBK").length;
			
			if(length > admiralNameMaxLong)
				throw new AdmiralNameIsTooLongException();
			
		} catch (UnsupportedEncodingException e) {
			throw new AdmiralNameIncludeHarmonyException();
		}
		
		// 舰队长名不允许包含特殊字符或被和谐词
		Matcher matcher = pattern.matcher(admiralName);
		if (matcher.find())
			throw new AdmiralNameIncludeHarmonyException();
		return;
	}
}
