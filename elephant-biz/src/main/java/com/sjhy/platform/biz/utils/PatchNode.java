package com.sjhy.platform.biz.utils;


public class PatchNode {
	  public int index = 0;
      public int len = 0;
      public byte[] bytesPatch = null;

      public int compare(PatchNode pn)
      {
          int j = 0;
          if (len != pn.len)
              return -1;
          for (int i = 0; i < len; i++)
          {
              if (bytesPatch[i] != pn.bytesPatch[i])
                  j++;
          }
          return j;
      }
}
