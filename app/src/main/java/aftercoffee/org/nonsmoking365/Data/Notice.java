package aftercoffee.org.nonsmoking365.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2015-11-11.
 */
public class Notice {
    public String name;
    public int count;
    public int page;
    @SerializedName("docs")
    public List<NoticeDocs> docsList;
}
