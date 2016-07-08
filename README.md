# 本地持久化缓存 - Android #

#### 相信大家都用过《今日头条》app，在没有网络的情况下它仍然可以加载相关新闻数据。供大家阅读，这里实际就是使用了本地缓存的策略来解决没有网络情况下的数据加载。


#### 提供保存字符串到本地 ####

public void put(String key, String value)()

public String getAsString(String key){}

#### 提供保存JSON到本地 ####

public void put(String key, JSONObject json){}

public JSONObject getAsJSON(String key){}

#### 提供保存JSON数组 ####

public void put(String key, JSONArray jA){}

public JSONArray getAsJSONArray(String key){}

#### 提供保存byte[]类型数组 ####

public void put(String key, byte[] byte){}

public byte[] getAsBytes(){}

#### 提供保存Bitmap对象 ####

public void put(String key, Bitmap bitmap){}

public Bitmap getAsBitmap(String key){}

#### 提供保存Drawable对象 ####

public void put(String key, Drawable drawable){}

public Drawable getAsDrawable(String key){}

####提供保存Serializable类型对象####

public void put(String key, Serializable serializable){}

public Drawable getAsSerializable(String key){}


......
......








 
 