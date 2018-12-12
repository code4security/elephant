package com.sjhy.platform.client.utils;

import com.kairo.gameserver.proto.clientproto.C2GS_UpdateGameData_Request.C2GS_UpdateGameData_Req;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameDataPatch {
	
	 public static int WordLen = 64;
   
     
     public static byte[] LastSystemData = null;
     public static byte[] LastGameData = null;

     public static void SetLastGameData(byte[] gd)
     {
         LastGameData = gd;
     }
     
 
     public static int CompareNum(int index, int index2, byte[] OldData, byte[] NewData)
     {
         int num = 0;
         for (int i = index, j = index2; i < OldData.length && j < NewData.length; i++, j++)
         {
             if (OldData[i] != NewData[j])
             {
                 num++;
             }
             else
                 break;
         }
         return num;
     }
     public static boolean NotBigThenData(int index, int index2, byte[] OldData, byte[] NewData)
     {
         if (index < OldData.length && index2 < NewData.length)
             return true;
         return false;
     }
     public static int GetLeftFixNextData(int index, int index2, byte[] OldData, byte[] NewData)  //左侧少，右侧多
     {
         for (int istart = index, jstart = index2; jstart < NewData.length; jstart++)
         {
             boolean isOK = true;
             for (int i = istart, j = jstart; i < istart + WordLen && NotBigThenData(i, j, OldData, NewData); i++, j++)
             {
                 if (OldData[i] != NewData[j])
                     isOK = false;
             }
             if (isOK)
                 return jstart;
         }
         return -1;
     }
     public static int GetRightFixNextData(int index, int index2, byte[] OldData, byte[] NewData) //右侧少，左侧多
     {
         for (int istart = index, jstart = index2; istart < OldData.length; istart++)
         {
             boolean isOK = true;
             for (int i = istart, j = jstart; j < jstart + WordLen && NotBigThenData(i, j, OldData, NewData); i++, j++)
             {
                 if (OldData[i] != NewData[j])
                     isOK = false;
             }
             if (isOK)
                 return istart;
         }
         return -1;
     }
     ///压缩处理
     public static byte[] DoCompressionData(byte []compressData) throws Exception
     {
    	 InputStream inStream = new ByteArrayInputStream(compressData);
 	 	ByteArrayOutputStream outStream=new ByteArrayOutputStream();
			
 	 	
    	  com.kairo.gameserver.SevenZip.Compression.LZMA.Encoder encoder = new com.kairo.gameserver.SevenZip.Compression.LZMA.Encoder();
			if (!encoder.SetAlgorithm(2))
				throw new Exception("Incorrect compression mode");
			if (!encoder.SetDictionarySize(1<<23))
				throw new Exception("Incorrect dictionary size");
			if (!encoder.SetNumFastBytes(32))
				throw new Exception("Incorrect -fb value");
			if (!encoder.SetMatchFinder(1))  //  {"BT2","BT4",}
				throw new Exception("Incorrect -mf value");
			if (!encoder.SetLcLpPb(3,0, 2))  //  LitContextBits  LitPosBits  PosStateBits
				throw new Exception("Incorrect -lc or -lp or -pb value");
			encoder.SetEndMarkerMode(false);
			
			encoder.WriteCoderProperties(outStream);
			long fileSize;
			
			fileSize = compressData.length;
			
			for (int i = 0; i < 8; i++)
				outStream.write((int)(fileSize >>> (8 * i)) & 0xFF);
			encoder.Code(inStream, outStream, -1, -1, null);
			
			int outLen=outStream.size();
			byte[] outData=outStream.toByteArray();
			
			return outData;
     }
     ///  解压处理
     ///
     public static byte[] DoDecompressionData(byte []compressData) throws Exception
     {
    	 	InputStream inStream = new ByteArrayInputStream(compressData);
    	 	ByteArrayOutputStream outStream=new ByteArrayOutputStream();
			
    	 	int propertiesSize = 5;
			byte[] properties = new byte[propertiesSize];
			
			
			if (inStream.read(properties, 0, propertiesSize) != propertiesSize)
				return null;
			com.kairo.gameserver.SevenZip.Compression.LZMA.Decoder decoder = new com.kairo.gameserver.SevenZip.Compression.LZMA.Decoder();
			if (!decoder.SetDecoderProperties(properties))
				throw new Exception("Incorrect stream properties");
			long outSize = 0;
			for (int i = 0; i < 8; i++)
			{
				int v = inStream.read();
				if (v < 0)
					throw new Exception("Can't read stream size");
				outSize |= ((long)v) << (8 * i);
			}
			if (!decoder.Code(inStream, outStream, outSize))
				throw new Exception("Error in data stream");
			
			int outLen=outStream.size();
			byte[] outData=outStream.toByteArray();
			
			return outData;
     }
     public static byte[] DoPatchFile(byte[] OldData, int newLen, String md5Str, byte[] patchCompressionData, List<C2GS_UpdateGameData_Req.PatchNode> _PatchNodeList, List<C2GS_UpdateGameData_Req.PatchNode> _ExtNodeList, List<C2GS_UpdateGameData_Req.PatchNode> _RemoveNodeList)
     {
    	 // 
    	 byte[] DecompressionData=null;
    	 try
    	 {
    		 DecompressionData= DoDecompressionData(patchCompressionData);  // Decompression use patchCompressionData
    	 }
    	 catch(Exception e)
    	 {
    		 return null;
    	 }
    	 
    	 if(DecompressionData==null)
    		 return null;
    	 int patchDataLen=0;
    	 List<PatchNode> PatchNodeList=new ArrayList<PatchNode>();
    	 List<PatchNode> ExtNodeList=new ArrayList<PatchNode>();
    	 List<PatchNode> RemoveNodeList=new ArrayList<PatchNode>();
    	 
    	 
    	 for(int i=0;i<_PatchNodeList.size();i++)
    	 {
    		 PatchNode pn=new PatchNode();
    		 pn.index=_PatchNodeList.get(i).getIndex();
    		 pn.len=_PatchNodeList.get(i).getLen();
    		 
    		 PatchNodeList.add(pn);
    		 patchDataLen=patchDataLen+pn.len;
    	 }
    	 
    	 for(int i=0;i<_ExtNodeList.size();i++)
    	 {
    		 PatchNode pn=new PatchNode();
    		 pn.index=_ExtNodeList.get(i).getIndex();
    		 pn.len=_ExtNodeList.get(i).getLen();
    		 
    		 ExtNodeList.add(pn);		 
    		 patchDataLen=patchDataLen+pn.len;    		 
    	 }
    	 
    	 for(int i=0;i<_RemoveNodeList.size();i++)
    	 {
    		 PatchNode pn=new PatchNode();
    		 pn.index=_RemoveNodeList.get(i).getIndex();
    		 pn.len=_RemoveNodeList.get(i).getLen();
    		 
    		 RemoveNodeList.add(pn);    		 
    	 }
    	 
    	 if(DecompressionData.length!=patchDataLen)
    		 return null;
    	 
    	 int deIndex=0;   	 
    	 
    	 for(int i=0;i<PatchNodeList.size();i++)
    	 {
    		 PatchNode c=PatchNodeList.get(i);    		 
    		 c.bytesPatch=new byte[c.len];
    		 for(int j=0;j<c.len;j++)
    		 {
    			 c.bytesPatch[j]=   DecompressionData[deIndex]; 
    			 deIndex++;
    		 }    		 
    	 }
    	 for(int i=0;i<ExtNodeList.size();i++)
    	 {
    		 PatchNode c=ExtNodeList.get(i);    		 
    		 c.bytesPatch=new byte[c.len];
    		 for(int j=0;j<c.len;j++)
    		 {
    			 c.bytesPatch[j]=   DecompressionData[deIndex]; 
    			 deIndex++;
    		 }    		 
    	 }
    	 
    	 return DoPatchFile( OldData,  newLen, md5Str, PatchNodeList,   ExtNodeList, RemoveNodeList);
     }
     
     public static byte[] DoPatchFile(byte[] OldData, int newLen, String md5Str, List<PatchNode> PatchNodeList, List<PatchNode> ExtNodeList, List<PatchNode> RemoveNodeList)
     { 
    	  //Check patch nodelist data
         int byteLen = OldData.length;
         for (int i = 0; i < ExtNodeList.size(); i++)
         {
             byteLen += ExtNodeList.get(i).len;
         }
         int byteMaxLen = byteLen;
         for (int i = 0; i < RemoveNodeList.size(); i++)
         {
             byteLen -= RemoveNodeList.get(i).len;
         }
                  
         if(byteLen!=newLen)
        	 return null;
         
         
         byte[] ReNewFileData = new byte[byteLen];

       
         int exIndex = 0;
         int reIndex = 0;
         int paIndex = 0;
         int newIndex = 0;

         for (int i = 0; i < OldData.length; i++)
         {
             if (ExtNodeList.size() > exIndex && i == ExtNodeList.get(exIndex).index)
             {
                 for (int j = 0; j < ExtNodeList.get(exIndex).len; j++)
                 {
                     ReNewFileData[newIndex] = ExtNodeList.get(exIndex).bytesPatch[j];
                     newIndex++;
                 }
                 exIndex++;
             }
             else if (RemoveNodeList.size() > reIndex && i == RemoveNodeList.get(reIndex).index)
             {
                 i = i + RemoveNodeList.get(reIndex).len;
                 reIndex++;
                 
                 if (i >= OldData.length)
                     break;
                 
             }

             if (PatchNodeList.size() > paIndex && i == PatchNodeList.get(paIndex).index)
             {
                 for (int j = 0; j < PatchNodeList.get(paIndex).len; j++)
                 {
                     ReNewFileData[newIndex] = PatchNodeList.get(paIndex).bytesPatch[j];
                     newIndex++;
                 }
                 i = i + PatchNodeList.get(paIndex).len - 1;
                 paIndex++;
             }
             else
             {
                 ReNewFileData[newIndex] = OldData[i];
                 newIndex++;
             }
         }
         if (newIndex < byteLen)
         {
             if (ExtNodeList.size() > exIndex && OldData.length == ExtNodeList.get(exIndex).index)
             {
                 for (int j = 0; j < ExtNodeList.get(exIndex).len; j++)
                 {
                     ReNewFileData[newIndex] = ExtNodeList.get(exIndex).bytesPatch[j];
                     newIndex++;
                 }
                 exIndex++;
             }
         }
         
         String newMd5Str =MD5Util.me().md5Base64(ReNewFileData);
         if(!newMd5Str.equals(md5Str))
        	 return null;
         
    	 return ReNewFileData;
    	 
     }
     public static boolean GetPatchFile(byte[] OldData, byte[] NewData, List<PatchNode> PatchNodeList, List<PatchNode> ExtNodeList, List<PatchNode> RemoveNodeList)
     {
//         PatchNodeList.clear();
//         ExtNodeList.clear();
//         RemoveNodeList.clear();
//
//         //计算patch，ext，remove
//         for (int i = 0, j = 0; i < OldData.length && j < NewData.length; i++, j++)
//         {
//             int k = CompareNum(i, j, OldData, NewData);
//             if (k == 0)
//             {
//
//             }
//             else if (k < 3)
//             {
//                 PatchNode pn = new PatchNode();
//                 pn.index = i;
//                 pn.len = k;
//             //for (int pi = i, pj = j,pk=0;pk<k ; pi++, pj++,pk++)
//                 pn.bytesPatch = new byte[pn.len];
//                 for (int pi = i, pj = j, pk = 0; pk < k; pi++, pj++, pk++)
//                 {
//                     pn.bytesPatch[pk] = NewData[pj];
//                 }
//                 i = i + k - 1;
//                 j = j + k - 1;
//
//                 PatchNodeList.add(pn);
//             }
//             else
//             {
//                 int jp = GetLeftFixNextData(i, j, OldData, NewData);
//                 if (jp == -1)
//                 {
//                     int ip = GetRightFixNextData(i, j, OldData, NewData);
//                     if (ip == -1)
//                     {
//                         //  大于K   3
//                         //else if (k < 3)
//                         {
//                             PatchNode pn = new PatchNode();
//                             pn.index = i;
//                             pn.len = k;
//                             //for (int pi = i, pj = j,pk=0;pk<k ; pi++, pj++,pk++)
//                             pn.bytesPatch = new byte[pn.len];
//                             for (int pi = i, pj = j, pk = 0; pk < k; pi++, pj++, pk++)
//                             {
//                                 pn.bytesPatch[pk] = NewData[pj];
//                             }
//                             i = i + k - 1;
//                             j = j + k - 1;
//
//                             PatchNodeList.add(pn);
//                         }
//                     }
//                     else
//                     {
//                         PatchNode pn = new PatchNode();
//                         pn.index = i;
//                         pn.len = ip - i;
//                         pn.bytesPatch = null;
//                         RemoveNodeList.add(pn);
//
//                         i = ip - 1;
//                         j--;
//                     }
//                 }
//                 else
//                 {
//                     PatchNode pn = new PatchNode();
//                     pn.index = i;
//                     pn.len = jp - j;
//                     pn.bytesPatch = new byte[pn.len];
//                     for (int exLj = j, pi = 0; exLj < jp; exLj++, pi++)
//                     {
//                         pn.bytesPatch[pi] = NewData[exLj];
//                     }
//                     ExtNodeList.add(pn);
//
//                     i--;
//                     j = jp - 1;
//                 }
//             }
//         }
//         
//
//
//         //Check patch nodelist data
//         int byteLen = OldData.length;
//         for (int i = 0; i < ExtNodeList.size(); i++)
//         {
//             byteLen += ExtNodeList.get(i).len;
//         }
//         int byteMaxLen = byteLen;
//         for (int i = 0; i < RemoveNodeList.size(); i++)
//         {
//             byteLen -= RemoveNodeList.get(i).len;
//         }
//
//         if (byteLen != NewData.length)
//             return false;
//
//         byte[] ReNewFileData = new byte[byteLen];
//
//       
//         int exIndex = 0;
//         int reIndex = 0;
//         int paIndex = 0;
//         int newIndex = 0;
//
//         for (int i = 0; i < OldData.length; i++)
//         {
//             if (ExtNodeList.size() > exIndex && i == ExtNodeList.get(exIndex).index)
//             {
//                 for (int j = 0; j < ExtNodeList.get(exIndex).len; j++)
//                 {
//                     ReNewFileData[newIndex] = ExtNodeList.get(exIndex).bytesPatch[j];
//                     newIndex++;
//                 }
//                 exIndex++;
//             }
//             else if (RemoveNodeList.size() > reIndex && i == RemoveNodeList.get(reIndex).index)
//             {
//                 i = i + RemoveNodeList.get(reIndex).len;
//                 reIndex++;
//             }
//
//             if (PatchNodeList.size() > paIndex && i == PatchNodeList.get(paIndex).index)
//             {
//                 for (int j = 0; j < PatchNodeList.get(paIndex).len; j++)
//                 {
//                     ReNewFileData[newIndex] = PatchNodeList.get(paIndex).bytesPatch[j];
//                     newIndex++;
//                 }
//                 i = i + PatchNodeList.get(paIndex).len - 1;
//                 paIndex++;
//             }
//             else
//             {
//                 ReNewFileData[newIndex] = OldData[i];
//                 newIndex++;
//             }
//         }
//
//         for (int i = 0; i < NewData.length; i++)
//         {
//             if (ReNewFileData[i] != NewData[i])
//                 return false;
//         }
         return true;
     }
	
	
	
}
