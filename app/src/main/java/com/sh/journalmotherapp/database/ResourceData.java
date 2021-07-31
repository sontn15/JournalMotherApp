package com.sh.journalmotherapp.database;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.sh.journalmotherapp.model.NewsModel;
import com.sh.journalmotherapp.model.PostModel;
import com.sh.journalmotherapp.model.UserModel;
import com.sh.journalmotherapp.util.CommonUtil;
import com.sh.journalmotherapp.util.Const;

public class ResourceData {

    public static void addPostTest(Context context) {

        String id = CommonUtil.generateUUID();
        String createdDate = CommonUtil.getCurrentDateStr();

        MySharedPreferences preferences = new MySharedPreferences(context);
        UserModel userModel = preferences.getUserLogin(Const.KEY_SHARE_PREFERENCE.USER_LOGIN);

        PostModel postModel1 = new PostModel();
        postModel1.setId(id);
        postModel1.setTitle("Mẹ bầu mang thai 3 tháng giữa nếu thấy có 3 biểu hiện này khi ngủ hãy kiểm tra ngay sức khỏe thai nhi");
        postModel1.setContent("3 tháng giữa thai kỳ kéo dài từ tuần thai 13 đến tuần thai 38. Đây là giai đoạn mà mẹ bầu sẽ cảm nhận rõ hơn sự tồn tại của thai nhi thông qua sự chuyển động trong bụng. Các triệu chứng ốm nghén cũng giảm dần và biến mất. Vì vậy mà mẹ bầu sẽ cảm thấy vô cùng hạnh phúc.\nTuy nhiên, mẹ bầu cũng có thể phải đối mặt với nhiều sự thay đổi như đau bụng dưới, đau lưng, chảy máu nưới răng, cơn gò Braxton-Hicks, bầu ngực to ra, nghẹt mũi, chóng mặt, giảm tần suất đi tiểu, lông, tóc phát triển nhanh, ợ chua, táo bón, trĩ, chuột rút chân, sự thay đổi về da, giãn tĩnh mạch, nhiễm trùng đường tiết niệu, tăng cân,…\nNếu đi ngủ mà mẹ bầu gặp phải 3 vấn đề này thì có thể ảnh hưởng đến sự phát triển của thai nhi. Mẹ bầu cần đi khám bác sĩ ngay.");
        postModel1.setCreatedDate(createdDate);
        postModel1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/journalmotherapp.appspot.com/o/ic_app_512.png?alt=media&token=4b1c2535-1d28-488a-9b24-7570fb0c4f05");
        postModel1.setAuthor(userModel);

        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.POSTS).child(id).setValue(postModel1);

    }

    public static void addNews() {
        NewsModel newsModel = new NewsModel();
        String id1 = CommonUtil.generateUUID();
        newsModel.setId(id1);
        newsModel.setTitle("Vitamin và khoáng chất cần thiết cho bà bầu");
        newsModel.setContent("Bà bầu cần 1.200 mg canxi, 30-60 mg sắt, ít nhất 400 mcg axit folic, vitamin D là 20 mcg… trong một ngày để thai nhi phát triển, hạn chế nguy cơ sẩy thai, sinh non.  ");
        newsModel.setCategory(Const.FirebaseRef.NEWS_FOOD);
        newsModel.setImageUrl("https://i1-suckhoe.vnecdn.net/2019/10/22/hinh-anh-2-dinh-duong-15717366-7075-1610-1571739277.png?w=500&h=300&q=100&dpr=1&fit=crop&s=OrrInoyPcrcaM8kpAuifAw");

        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).child(Const.FirebaseRef.NEWS_FOOD).child(id1).setValue(newsModel);

        NewsModel newsModel2 = new NewsModel();
        String id2 = CommonUtil.generateUUID();
        newsModel2.setId(id2);
        newsModel2.setTitle("Bà bầu không nên ăn khoai tây chiên");
        newsModel2.setContent("Thai phụ ăn nhiều khoai tây chiên có thể tăng lượng axit linoleic, gây hại cho bản thân và em bé trong bụng. ");
        newsModel2.setCategory(Const.FirebaseRef.NEWS_MOM);
        newsModel2.setImageUrl("https://i1-suckhoe.vnecdn.net/2019/07/23/700173328h-1563853071-15638530-3719-6904-1563853377.png?w=500&h=300&q=100&dpr=1&fit=crop&s=ajQj4FQKKVWMvjgL8N66Dg");

        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).child(Const.FirebaseRef.NEWS_MOM).child(id2).setValue(newsModel2);

        NewsModel newsModel3 = new NewsModel();
        String id3 = CommonUtil.generateUUID();
        newsModel3.setId(id3);
        newsModel3.setTitle("Mẹ trở dạ trên trời, con được miễn phí bay 21 năm");
        newsModel3.setContent("Hãng hàng không quyết định em bé sinh trên máy bay sẽ được sử dụng dịch vụ miễn phí trọn đời. ");
        newsModel3.setCategory(Const.FirebaseRef.NEWS_BABY);
        newsModel3.setImageUrl("https://i1-dulich.vnecdn.net/2017/06/20/turkishbabycturkishairlines-1497945712.jpg?w=500&h=300&q=100&dpr=1&fit=crop&s=bKPAzA2efziGd90lTI6oWg");

        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).child(Const.FirebaseRef.NEWS_BABY).child(id3).setValue(newsModel3);
    }

}
