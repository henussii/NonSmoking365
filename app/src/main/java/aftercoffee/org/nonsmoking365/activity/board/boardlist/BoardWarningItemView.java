package aftercoffee.org.nonsmoking365.activity.board.boardlist;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import aftercoffee.org.nonsmoking365.R;
import aftercoffee.org.nonsmoking365.data.LikesResult;
import aftercoffee.org.nonsmoking365.manager.NetworkManager;
import aftercoffee.org.nonsmoking365.manager.UserManager;

/**
 * Created by Tacademy on 2015-11-09.
 */
public class BoardWarningItemView extends FrameLayout {

    Context context;
    int position;

    String docID;
    String user_id;
    int likes;
    boolean likeOn;

    public BoardWarningItemView(Context context, String docID, int position) {
        super(context);
        this.context = context;
        this.docID = docID;
        this.position = position;
        this.user_id = UserManager.getInstance().getUser_id();
        init();
    }

    ImageView titleImageView;
    TextView titleTextView;
    TextView contentsTextView;
    Button likeBtn;
    ImageView likeImage;
    Button commentsBtn;
    Button shareBtn;

    /**
     * Board의 글 목록 ITEM
     */

    // ITEM의 click listener를 interface로 정의.
    public interface OnWarningBtnClickListener {
        public void onWarningLikeBtnClick(View view, int position, int likes, boolean likeOn);
        public void onWarningCommentBtnClick(View view);
        public void onWarningShareBtnClick(View view);
    }
    public OnWarningBtnClickListener mListener;
    public void setOnWarningBtnClickListener(OnWarningBtnClickListener listener) {
        mListener = listener;
    }

    public void init() {
        inflate(getContext(), R.layout.view_board_warning_item, this);

        titleImageView = (ImageView)findViewById(R.id.image_title);
        titleTextView = (TextView)findViewById(R.id.text_title);
        contentsTextView = (TextView)findViewById(R.id.text_contents);

        likeBtn = (Button)findViewById(R.id.btn_like);
        likeImage = (ImageView)findViewById(R.id.image_like);
        commentsBtn = (Button)findViewById(R.id.btn_comment);
        shareBtn = (Button)findViewById(R.id.btn_share);

        // 좋아요 버튼
        likeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance().postBoardLike(context, docID, user_id, new NetworkManager.OnResultListener<LikesResult>() {
                    @Override
                    public void onSuccess(LikesResult result) {
                        likes = result.like_ids.size();
                        likeOn = false;
                        for (String s : result.like_ids) {
                            if (user_id.equals(s)) {
                                likeOn = true;
                                break;
                            } else {
                                likeOn = false;
                            }
                        }
                        likeBtnRefresh(likes, likeOn);
                        // Adapter에게 알리자
                        mListener.onWarningLikeBtnClick(BoardWarningItemView.this, position, result.like_ids.size(), likeOn);
                    }

                    @Override
                    public void onFail(int code) {

                    }
                });
            }
        });
        // 댓글 버튼
        commentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onWarningCommentBtnClick(BoardWarningItemView.this);
            }
        });
        // 공유하기 버튼
        shareBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onWarningShareBtnClick(BoardWarningItemView.this);
            }
        });
    }

    public void setBoardItem(BoardWarningItem item) {
        titleImageView.setBackgroundResource(item.titleImg);
        titleTextView.setText(item.title);
        contentsTextView.setText(item.contents);
        likes = item.likes;
        likeOn = item.likeOn;
        likeBtnRefresh(likes, likeOn);
    }

    public void likeBtnRefresh(int likes, boolean likeOn) {
        if (likes > 999) {
            likeBtn.setText("좋아요 999+");
        } else {
            likeBtn.setText("좋아요 " + likes);
        }
        if (likeOn) {
            Log.d("dd", "oooooooooooooooooooooo");
            likeImage.setImageResource(R.drawable.icon_like_active);
        } else {
            Log.d("dd", "xxxxxxxxxxxxxxxxxxxxxx");
            likeImage.setImageResource(R.drawable.icon_like);
        }
    }
}
