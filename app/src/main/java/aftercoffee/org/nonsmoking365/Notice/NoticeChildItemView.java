package aftercoffee.org.nonsmoking365.Notice;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import aftercoffee.org.nonsmoking365.R;

/**
 * Created by Tacademy on 2015-11-11.
 */
public class NoticeChildItemView extends FrameLayout {
    public NoticeChildItemView(Context context) {
        super(context);
        init();
    }

    DisplayImageOptions options;

    ImageView noticeImageView;

    TextView contentsView;
    TextView imageUrlView;

    private void init() {
        inflate(getContext(), R.layout.view_notice_child_item, this);
        noticeImageView = (ImageView)findViewById(R.id.image_notice);
        contentsView = (TextView)findViewById(R.id.text_contentsView);

        imageUrlView = (TextView)findViewById(R.id.text_imageURL);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_image_add)
                .showImageForEmptyUri(R.drawable.icon_cigarette)
                .showImageOnFail(R.drawable.icon_cigarette)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
//                .displayer(new RoundedBitmapDisplayer(50))
                .build();
    }

    public void setChildItem(NoticeChildItem item) {
        contentsView.setText(item.contents);
        imageUrlView.setText(item.imageUrl);
        ImageLoader.getInstance().displayImage(item.imageUrl, noticeImageView, options);
    }
}
