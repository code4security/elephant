package com.sjhy.platform.biz.utils;

import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	private static final Double ERROR_CODE = -99999999d;
	
	private static SecureRandom sr;
	static {
		sr = new SecureRandom();
	}
	
	 /** 
     * Converts a line of text into an array of lower case words. Words are 
     * delimited by the following characters: , .\r\n:/\+ 
     * <p> 
     * In the future, this method should be changed to use a 
     * BreakIterator.wordInstance(). That class offers much more fexibility. 
     *  
     * @param text 
     *            a String of text to convert into an array of words 
     * @return text broken up into an array of words. 
     */  
    public static final String[] toLowerCaseWordArray(String text) {
        if (text == null || text.length() == 0) {  
            return new String[0];
        }  
        StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
        String[] words = new String[tokens.countTokens()];
        for (int i = 0; i < words.length; i++) {  
            words[i] = tokens.nextToken().toLowerCase();  
        }  
        return words;  
    }  
  
    /** 
     * Converts a line of text into an array of lower case words. Words are 
     * delimited by the following characters: , .\r\n:/\+ 
     * <p> 
     * In the future, this method should be changed to use a 
     * BreakIterator.wordInstance(). That class offers much more fexibility. 
     *  
     * @param text 
     *            a String of text to convert into an array of words 
     * @return text broken up into an array of words. 
     */  
    public static final String[] toStringArray(String text) {
        if (text == null || text.length() == 0) {  
            return new String[0];
        }  
        StringTokenizer tokens = new StringTokenizer(text, ",\r\n/\\");
        String[] words = new String[tokens.countTokens()];
        for (int i = 0; i < words.length; i++) {  
            words[i] = tokens.nextToken();  
        }  
        return words;  
    }  
  
    /** 
     * * Converts a line of text into an array of lower case words. Words are 
     * delimited by the following characters: , .\r\n:/\+ 
     * <p> 
     * In the future, this method should be changed to use a 
     * BreakIterator.wordInstance(). That class offers much more fexibility. 
     *  
     * @param text 
     *            a String of text to convert into an array of words 
     * @param token 
     *            String 
     * @return String[]broken up into an array of words. 
     */  
    public static final String[] toStringArray(String text, String token) {
        if (text == null || text.length() == 0) {  
            return new String[0];
        }  
        StringTokenizer tokens = new StringTokenizer(text, token);
        String[] words = new String[tokens.countTokens()];
        for (int i = 0; i < words.length; i++) {  
            words[i] = tokens.nextToken();  
        }  
        return words;  
    }  
  
    /** 
     *  
     * @param source 
     * @return 
     */  
    public static String[] splitOnWhitespace(String source) {
        int pos = -1;  
        LinkedList<String> list = new LinkedList<String>();
        int max = source.length();  
        for (int i = 0; i < max; i++) {  
            char c = source.charAt(i);  
            if (Character.isWhitespace(c)) {
                if (i - pos > 1) {  
                    list.add(source.substring(pos + 1, i));  
                }  
                pos = i;  
            }  
        }  
        return list.toArray(new String[list.size()]);
    }  
  
    /** 
     * Replayer str 
     *  
     * @param str 
     * @param key 
     * @param replacement 
     * @return 
     */  
    public static final String replaceAll(String str, String key,
                                          String replacement) {
        if (str != null && key != null && replacement != null  
                && !str.equals("") && !key.equals("")) {  
            StringBuilder strbuf = new StringBuilder();
            int begin = 0;  
            int slen = str.length();  
            int npos = 0;  
            int klen = key.length();  
            for (; begin < slen && (npos = str.indexOf(key, begin)) >= begin; begin = npos  
                    + klen) {  
                strbuf.append(str.substring(begin, npos)).append(replacement);  
            }  
  
            if (begin == 0) {  
                return str;  
            }  
            if (begin < slen) {  
                strbuf.append(str.substring(begin));  
            }  
            return strbuf.toString();  
        } else {  
            return str;  
        }  
    }  
      
      
    public static String UnicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;     
        boolean hasU = false;  
        while (matcher.find()) {     
            hasU = true;  
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");      
        }   
        String s = str;
        try{  
            if(!hasU){  
                int i = 0;  
                String rstr = "";
                while(i+4<=str.length()){  
                    ch = (char) Integer.parseInt(str.substring(i,i=i+4), 16);
                    rstr = rstr+ch;  
                }  
                str = rstr;  
            }  
        }catch(Exception ex){
            str = s;  
            ex.printStackTrace();  
        }  
        return str;     
    }   
    /** 空字符串。 */  
    public static final String EMPTY_STRING = "";
  
    /** 
     * 比较两个字符串（大小写敏感）。 
     * <pre> 
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre> 
     * 
     * @param str1 要比较的字符串1 
     * @param str2 要比较的字符串2 
     * 
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code> 
     */  
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {  
            return str2 == null;  
        }  
  
        return str1.equals(str2);  
    }  
  
    /** 
     * 比较两个字符串（大小写不敏感）。 
     * <pre> 
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true
     * </pre> 
     * 
     * @param str1 要比较的字符串1 
     * @param str2 要比较的字符串2 
     * 
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code> 
     */  
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {  
            return str2 == null;  
        }  
  
        return str1.equalsIgnoreCase(str2);  
    }  
  
    /** 
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。 
     * <pre> 
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre> 
     * 
     * @param str 要检查的字符串 
     * 
     * @return 如果为空白, 则返回<code>true</code> 
     */  
    public static boolean isBlank(String str) {
        int length;  
  
        if ((str == null) || ((length = str.length()) == 0)) {  
            return true;  
        }  
  
        for (int i = 0; i < length; i++) {  
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;  
            }  
        }  
  
        return true;  
    }  
  
    /** 
     * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。 
     * <pre> 
     * StringUtils.isBlank(null)      = false
     * StringUtils.isBlank("")        = false
     * StringUtils.isBlank(" ")       = false
     * StringUtils.isBlank("bob")     = true
     * StringUtils.isBlank("  bob  ") = true
     * </pre> 
     * 
     * @param str 要检查的字符串 
     * 
     * @return 如果为空白, 则返回<code>true</code> 
     */  
    public static boolean isNotBlank(String str) {
        int length;  
  
        if ((str == null) || ((length = str.length()) == 0)) {  
            return false;  
        }  
  
        for (int i = 0; i < length; i++) {  
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;  
            }  
        }  
  
        return false;  
    }  
  
    /** 
     * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。 
     * <pre> 
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre> 
     * 
     * @param str 要检查的字符串 
     * 
     * @return 如果为空, 则返回<code>true</code> 
     */  
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));  
    }  
  
    /** 
     * 检查字符串是否不是<code>null</code>和空字符串<code>""</code>。 
     * <pre> 
     * StringUtils.isEmpty(null)      = false
     * StringUtils.isEmpty("")        = false
     * StringUtils.isEmpty(" ")       = true
     * StringUtils.isEmpty("bob")     = true
     * StringUtils.isEmpty("  bob  ") = true
     * </pre> 
     * 
     * @param str 要检查的字符串 
     * 
     * @return 如果不为空, 则返回<code>true</code> 
     */  
    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));  
    }  
  
    /** 
     * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。 
     * <pre> 
     * StringUtils.indexOf(null, *)          = -1
     * StringUtils.indexOf(*, null)          = -1
     * StringUtils.indexOf("", "")           = 0
     * StringUtils.indexOf("aabaabaa", "a")  = 0
     * StringUtils.indexOf("aabaabaa", "b")  = 2
     * StringUtils.indexOf("aabaabaa", "ab") = 1
     * StringUtils.indexOf("aabaabaa", "")   = 0
     * </pre> 
     * 
     * @param str 要扫描的字符串 
     * @param searchStr 要查找的字符串 
     * 
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code> 
     */  
    public static int indexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {  
            return -1;  
        }  
  
        return str.indexOf(searchStr);  
    }  
  
    /** 
     * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。 
     * <pre> 
     * StringUtils.indexOf(null, *, *)          = -1
     * StringUtils.indexOf(*, null, *)          = -1
     * StringUtils.indexOf("", "", 0)           = 0
     * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtils.indexOf("aabaabaa", "b", -1) = 2
     * StringUtils.indexOf("aabaabaa", "", 2)   = 2
     * StringUtils.indexOf("abc", "", 9)        = 3
     * </pre> 
     * 
     * @param str 要扫描的字符串 
     * @param searchStr 要查找的字符串 
     * @param startPos 开始搜索的索引值，如果小于0，则看作0 
     * 
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code> 
     */  
    public static int indexOf(String str, String searchStr, int startPos) {
        if ((str == null) || (searchStr == null)) {  
            return -1;  
        }  
  
        // JDK1.3及以下版本的bug：不能正确处理下面的情况  
        if ((searchStr.length() == 0) && (startPos >= str.length())) {  
            return str.length();  
        }  
  
        return str.indexOf(searchStr, startPos);  
    }  
  
    /** 
     * 取指定字符串的子串。 
     *  
     * <p> 
     * 负的索引代表从尾部开始计算。如果字符串为<code>null</code>，则返回<code>null</code>。 
     * <pre> 
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
     * </pre> 
     * </p> 
     * 
     * @param str 字符串 
     * @param start 起始索引，如果为负数，表示从尾部计算 
     * @param end 结束索引（不含），如果为负数，表示从尾部计算 
     * 
     * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code> 
     */  
    public static String substring(String str, int start, int end) {
        if (str == null) {  
            return null;  
        }  
  
        if (end < 0) {  
            end = str.length() + end;  
        }  
  
        if (start < 0) {  
            start = str.length() + start;  
        }  
  
        if (end > str.length()) {  
            end = str.length();  
        }  
  
        if (start > end) {  
            return EMPTY_STRING;  
        }  
  
        if (start < 0) {  
            start = 0;  
        }  
  
        if (end < 0) {  
            end = 0;  
        }  
  
        return str.substring(start, end);  
    }  
  
    /** 
     * 检查字符串中是否包含指定的字符串。如果字符串为<code>null</code>，将返回<code>false</code>。 
     * <pre> 
     * StringUtils.contains(null, *)     = false
     * StringUtils.contains(*, null)     = false
     * StringUtils.contains("", "")      = true
     * StringUtils.contains("abc", "")   = true
     * StringUtils.contains("abc", "a")  = true
     * StringUtils.contains("abc", "z")  = false
     * </pre> 
     * 
     * @param str 要扫描的字符串 
     * @param searchStr 要查找的字符串 
     * 
     * @return 如果找到，则返回<code>true</code> 
     */  
    public static boolean contains(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {  
            return false;  
        }  
  
        return str.indexOf(searchStr) >= 0;  
    }  
  
    /** 
     * <p>Checks if the String contains only unicode digits. 
     * A decimal point is not a unicode digit and returns false.</p> 
     * 
     * <p><code>null</code> will return <code>false</code>. 
     * An empty String ("") will return <code>true</code>.</p> 
     * 
     * <pre> 
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre> 
     * 
     * @param str  the String to check, may be null 
     * @return <code>true</code> if only contains digits, and is non-null 
     */  
    public static boolean isNumeric(String str) {
        if (str == null) {  
            return false;  
        }  
        int sz = str.length();  
        for (int i = 0; i < sz; i++) {  
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;  
            }  
        }  
        return true;  
    }  
  
    /** 
     * 字符串拼接 
     * @param object 
     * @return 
     */  
    public static String assemble(char sep, Object... object){
        if(object == null)return null;  
        StringBuilder sb = new StringBuilder();
        for(Object obj:object){
            if(obj == null)obj="";  
            sb.append(obj.toString()).append(sep);  
        }  
        String str = "";
        if(sb.length()>0){  
            str = sb.substring(0, sb.length()-1);  
        }  
        return str;  
    }  
  
    // 6-16个字母和数字组成  
    private static String regex = "^[A-Za-z0-9]$";
    /** 
     * 检测字符串是否符合规则（6-16个字母和数字组成，大小写不敏感） 
     * @param user 
     * @return 
     */  
    public static boolean checkStringLegal(String user) {
        boolean isMatch = true;  
        char[] userChars = user.toCharArray();  
        for(char c : userChars){  
            isMatch = String.valueOf(c).matches(regex);
            if(!isMatch){  
                break;  
            }  
        }  
        return isMatch;  
    }  
      
  
    public static String getString(String input) {
        return getString(input, true, "");  
    }  
  
    public static String getString(String input, boolean btrim, String dval) {
        if (input == null)  
            return dval;  
        try {  
            if (btrim)  
                return trim(input);  
            else  
                return input;  
        } catch (Exception e) {
            return "";  
        }  
    }  
  
    public static String Trim(String str) {
        return trim(str);  
    }  
  
    public static String[] Trim(String[] s) {
        return trim(s);  
    }  
  
    public static String trim(String str) {
        if (str == null)  
            return "";  
        else  
            return str.trim();  
    }  
  
    public static String[] trim(String[] s) {
        if (s == null || s.length <= 0)  
            return s;  
        for (int i = 0; i < s.length; i++)  
            s[i] = trim(s[i]);  
        return s;  
    }  
  
    public static int getInt(String input, boolean btrim, int dval) {
        if (input == null)  
            return dval;  
        int val;  
        try {  
            String str = new String(input);
            if (btrim)  
                str = trim(str);  
            val = Integer.parseInt(str);
        } catch (Exception e) {
            val = dval;  
        }  
        return val;  
    }  
      
    public static int[] getInts(String input) {
        return getInts(input, ",");  
    }  
  
    public static int[] getInts(String input, String split) {
        if (input == null) {  
            return null;  
        }  
          
        String[] ss = input.split(split);
        int[] ii = new int[ss.length];  
        for (int i=0;i<ii.length;i++) {  
            ii[i] = getInt(ss[i]);  
        }  
        return ii;  
    }  
  
    public static int getInt(String input) {
        return getInt(input, true, 0);  
    }  
    
    /**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return ERROR_CODE;
		}
		
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return ERROR_CODE;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}
	
	public static Long toLong(Object val, long _default){
		long result =  toDouble(val).longValue();
		
		if(result == ERROR_CODE.longValue()){
			return _default;
		}
		
		return result;
	}
	
	public static synchronized String getUniqueID(int len) {
		if (len < 1)
			return null;
		// final int UNIQUE_ID_LENGTH = 16;
		final char[] alphabet = ("12345abcdefghijklmnopqrstuvwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ67890").toCharArray();
		byte[] b = new byte[len];
		
		sr.nextBytes(b);
		
		char[] out = new char[len];
		for (int i = 0; i < len; i++) {
			int index = b[i] % alphabet.length;
			if (index < 0)
				index += alphabet.length;
			out[i] = alphabet[index];
		}
		
		return new String(out);
	}
	
	public static void main(String[] args) {
		Map<String, String> channel = new LinkedHashMap<String, String>();
//		channel.put("1100", "com.tencent.tmgp");
//		channel.put("1110", "com.tencent.tmgp.tx");
//		channel.put("1200", "qihoo");
//		channel.put("1300", "nearme.gamecenter");
//		channel.put("2000", "vivo");
//		channel.put("2100", "mz");
//		channel.put("2200", "yyh");
//		channel.put("1500", "baidu");
//		channel.put("1501", "baidu");
//		channel.put("1502", "baidu");
//		channel.put("1503", "baidu");
//		channel.put("2300", "huawei");
//		channel.put("1600", "lenovo");
//		channel.put("1800", "jinli");
//		channel.put("2400", "coolpad");
//		channel.put("1700", "uc");
//		channel.put("1701", "wdj");
//		channel.put("1400", "mi");
//		channel.put("1900", "anzhi");
//		channel.put("2600", "m4399");
//		channel.put("2500", "taptap");
//		channel.put("1000", "kairo");
//		channel.put("2700", "youpinwei");
//		channel.put("3100", "meitu");
//		channel.put("3200", "AcFun");
//		channel.put("3300", "downjoy");
//		channel.put("2900", "bili");
//		channel.put("3400", "intouch");
//		channel.put("1301", "ipc.nearme.gamecenter");
//		channel.put("2101", "ff.mz");
		channel.put("3500", "huluxia");
		channel.put("3600", "bmsq");
		channel.put("3700", "x7sy");
		channel.put("3800", "papa");
		channel.put("3900", "hykb");
		
		//String[] channel = {"com.tencent.tmgp", "qihoo", "nearme.gamecenter", "vivo", "mz", "yyh", "baidu", "huawei", "lenovo", "jinli", "coolpad", "uc", "mi", "anzhi", "m4399", "taptap", "kairo", "youpinwei"};
		//String[] channel = {"kairo"};
		
		//String[] pack = {"onsen","GameDev3","Frontier","School2","Depart","Cruise","restaurant","Paddock","edotowns","Soccoer","Soccoer2","Zaibatu3","Bouken","Fukuya","Ninja","Animestudio","Pirate","Noujou","Airplane","Baseball","Townshop","Fight","Ski","Bokujou","Densha","Pyramid","Recycle","Daishizen","Memo","WaiwaiGame","MangaIppon","ongaku2","Ccbokujou","Starkairo","Sushi","Mujin","Horse","Apart","Gamecenter","Okashi","Witchquest","Magazine","Snsdev","Pakukai","CatJump","Wizardquest","Noodle","Noodle2","Nakayoshi","tennis","Paddock2","XXXXXXXX","Trade","Artist","Picogame","Manga","Park"};
		String[] pack = {"onsen","GameDev3","Frontier","School2","Depart","Cruise","restaurant","Paddock","edotowns","Soccoer","Soccoer2","Zaibatu3","Bouken","Fukuya","Ninja","Animestudio","Pirate","Noujou","Airplane","Baseball","Townshop","Fight","Ski","Bokujou","Densha","Pyramid","Recycle","Daishizen","Memo","WaiwaiGame","MangaIppon","ongaku2","Ccbokujou","Starkairo","Sushi","Mujin","Horse","Apart","Gamecenter","Okashi","Witchquest","Magazine","Snsdev","Pakukai","CatJump","Wizardquest","Noodle","Noodle2","Nakayoshi","tennis","Paddock2","Tatakau","Trade","Artist","Picogame","Manga","Park","ParkDog","ThreeKing"};
		//String[] pack = {"ParkDog"};
		
		/**
		for(String _channel : channel){
			for(String _pack : pack){
				if("com.tencent.tmgp".equals(_channel)){
					System.out.println(_channel + ".kairogame.android." + _pack);
				}else{
					System.out.println("com.kairogame.android." + _pack + "." + _channel);
				}
			}
		}**/
		
		for(String _pack : pack){
			for(Entry<String, String> _channel : channel.entrySet()){
				if(_channel.getValue().indexOf("com.tencent.tmgp") >= 0){
					System.out.println(_pack + "|" + _channel.getKey() + "|" + _channel.getValue() + ".kairogame.android." + _pack);
				} else if (_channel.getValue().equals("kairo")) {
					System.out.println(_pack + "|" + _channel.getKey() + "|" + "com.kairogame.android." + _pack);
				} else if (_channel.getValue().equals("intouch")) {
					System.out.println(_pack + "|" + _channel.getKey() + "|" + ("net.kairosoft.android." + _pack+ "." + _channel.getValue()).toLowerCase());
				} else {
					System.out.println(_pack + "|" + _channel.getKey() + "|" + "com.kairogame.android." + _pack + "." + _channel.getValue());
				}
			}
		}
	}

//	public static void main(String[] args) {
//		String key = getUniqueID(32);
//		
//		System.out.println("JbJ9kk0nO8mDV9BVL5OOX40VH9dKesZW".toLowerCase());
//	}
}
