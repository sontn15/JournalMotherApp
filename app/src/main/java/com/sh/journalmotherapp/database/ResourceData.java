package com.sh.journalmotherapp.database;

import com.google.firebase.database.FirebaseDatabase;
import com.sh.journalmotherapp.model.NewsModel;
import com.sh.journalmotherapp.util.CommonUtil;
import com.sh.journalmotherapp.util.Const;

public class ResourceData {

    public static void addNews() {
        NewsModel newsModel = new NewsModel();
        String id1 = CommonUtil.generateUUID();
        newsModel.setId(id1);
        newsModel.setTitle("Vitamin và khoáng chất cần thiết cho bà bầu");
        newsModel.setContent("Bà bầu cần 1.200 mg canxi, 30-60 mg sắt, ít nhất 400 mcg axit folic, vitamin D là 20 mcg… trong một ngày để thai nhi phát triển, hạn chế nguy cơ sẩy thai, sinh non.  ");
        newsModel.setCategory(Const.FirebaseRef.NEWS_FOOD);
        newsModel.setImageUrl("https://i1-suckhoe.vnecdn.net/2019/10/22/hinh-anh-2-dinh-duong-15717366-7075-1610-1571739277.png?w=500&h=300&q=100&dpr=1&fit=crop&s=OrrInoyPcrcaM8kpAuifAw");

        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).child(Const.FirebaseRef.NEWS_FOOD).child(id1).setValue(newsModel);

        NewsModel newsFood1 = new NewsModel();
        String id11 = CommonUtil.generateUUID();
        newsFood1.setId(id11);
        newsFood1.setTitle("Dinh dưỡng trước và trong quá trình mang thai");
        newsFood1.setContent("Chế độ dinh dưỡng có vai trò rất lớn trước và trong quá trình mang thai. Theo đó, bạn có thể bổ sung với khuyến cáo sau đây:\n" +
                "\n" +
                "Chế độ ăn lành mạnh nói chung cho mẹ và em bé bao gồm: Rau, hoa quả sạch, ngũ cốc nguyên hạt, sữa ít béo, protein như thịt, cá, trứng, đậu... Nếu bạn không uống sữa, có thể bổ sung calci từ các nguồn thực phẩm khác.\n" +
                "Bạn có thể ăn một số loại cá và hải sản chứa rất ít thủy ngân. Các chuyên gia khuyên mỗi tuần, bạn nên ăn: 2-3 khẩu phần hải sản chứa ít thủy ngân như: tôm, cá hồi, cá ngừ đóng hộp...cá xanh, cá mú, cá bơn, cá ngừ vây vàng ...");
        newsFood1.setCategory(Const.FirebaseRef.NEWS_FOOD);
        newsFood1.setImageUrl("https://vinmec-prod.s3.amazonaws.com/images/20210319_220446_018345_dinh-duong-cho-phu-nu.max-800x800.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_FOOD).child(id11).setValue(newsFood1);

        NewsModel newsFood2 = new NewsModel();
        String id12 = CommonUtil.generateUUID();
        newsFood2.setId(id12);
        newsFood2.setTitle("Bạn muốn mang thai: Ăn như thế nào và ăn gì?");
        newsFood2.setContent("Một số loại thực phẩm dưới đây đã được chứng minh có khả năng hỗ trợ sinh sản, bao gồm:\n" +
                "\n" +
                "1.1. Hoa quả và rau xanh\n" +
                "Hãy coi hoa quả và rau xanh như một loại vitamin tổng hợp mà thiên nhiên ban tặng cho con người. Trái cây và rau quả cung cấp rất nhiều loại vitamin và khoáng chất và đặc biệt là bổ sung một số chất dinh dưỡng đặc biệt quan trọng trước khi người phụ nữ thụ thai.\n" +
                "\n" +
                "Các loại thực phẩm như rau bina, cải bruxen, măng tây, bắp cải, trái cây họ cam quýt, các loại hạt, các loại đậu, ngũ cốc nguyên hạt, bánh mì và ngũ cốc bổ sung rất nhiều vitamin B cho cơ thể. Vitamin B là một dạng acid folic tự nhiên, một chất dinh dưỡng thiết yếu trong các loại vitamin mà người phụ nữ cần nếu đang có kế hoạch mang thai. Ăn các loại thực phẩm giàu vitamin B trong thời kỳ chuẩn bị mang thai và mang thai có thể giúp ngăn ngừa dị tật bẩm sinh liên quan đến ống thần kinh như tật nứt đốt sống. Vitamin B có thể bị thất thoát trong quá trình nấu nướng do đó hãy cố gắng hấp hoặc luộc rau với một lượng nhỏ nước để có thể giữ được vitamin B.\n" +
                "\n" +
                "Nói chung, trái cây và các loại rau quả luôn là lựa chọn tốt nhất cho tất cả các chế độ ăn uống lành mạnh chứ không chỉ dành cho những người phụ nữ mang thai.\n" +
                "\n" +
                "1.2. Cá\n" +
                "Các loại cá béo như cá hồi, cá cơm, cá trích... là nguồn cung cấp acid béo omega -3 tốt nhất cho cơ thể. Theo một số nhà khoa học, những chất béo thiết yếu này có thể có tác động tích cực đến khả năng sinh sản của người phụ nữ. Các nghiên cứu cho thấy một chế độ ăn uống giàu acid béo omega – 3 có thể giúp điều hòa quá trình rụng trứng, cải thiện chất lượng tế bào trứng và thậm chí làm chậm sự lão hóa của buồng trứng.\n" +
                "\n" +
                "Một số người cho rằng cá chứa nhiều thủy ngân, một kim loại nặng có hại cho não và hệ thần kinh đang phát triển của trẻ. Tuy nhiên không phải tất cả các loại cá đều chứa nhiều thủy ngân. Cơ quan Quản lý Thực phẩm và Dược phẩm Hoa Kỳ cho biết những người phụ nữ đang có kế hoạch mang thai có thể ăn khoảng 3 khúc cá trong tuần mà không sợ ảnh hưởng tới thai nhi. Tuy nhiên Cơ quan Quản lý Thực phẩm và Dược phẩm Hoa Kỳ cũng đưa ra khuyến cáo về một số loại cá không tốt cho sức khỏe và nên hạn chế ăn như cá ngừ trắng và tránh hoàn toàn cá kiếm, cá nhám, cá ngói từ vịnh Mexico, cá thu, cá ngừ mắt to và cá mập vì đây đều là những loại cá có hàm lượng thủy ngân rất cao.\n" +
                "\n" +
                "Ngoài ra những người phụ nữ có kế hoạch mang thai cũng có thể uống bổ sung dầu cá nếu không muốn ăn trực tiếp. Tuy nhiên bạn cũng cần trao đổi với bác sĩ về loại dầu cá cũng như liều lượng sử dụng.1.3. Hàu biển\n" +
                "Trong khi những thực phẩm đã nêu bên trên có ảnh hưởng đến khả năng sinh sản nữ giới thì hàu biển lại có thể tăng cường khả năng sinh sản của cả nam giới và nữ giới. Hàu biển chứa nhiều kẽm, đóng một vai trò quan trọng trong việc sản xuất tinh dịch và hormone testosterone ở nam giới cũng như quá trình rụng trứng và khả năng sinh sản ở nữ giới. Tuy nhiên điều đó không có nghĩa là các cặp vợ chồng nên bổ sung hàu vào mỗi bữa ăn của họ. Duy trì lượng kẽm được khuyến nghị hàng ngày ở mức 8 mg mỗi ngày có thể giúp hệ sinh sản hoạt động bình thường, tuy nhiên quá nhiều kẽm có thể sẽ phản tác dụng và khiến khả năng sinh sản của cả hai giảm sút.\n" +
                "\n" +
                "1.4. Protein nguồn gốc thực vật\n" +
                "Protein là một phần quan trọng trong các chế độ ăn uống. Tuy nhiên nhiều người lại phụ thuộc quá nhiều vào nguồn protein động vật đến từ thịt bò, thịt lợn, thịt gà. Trong một nghiên cứu mới đây trên 18.555 người phụ nữ, các chuyên gia tại đại học Harvard đã phát hiện ra rằng những người thường xuyên bổ sung protein có nguồn gốc thực vật chẳng hạn như từ đậu Hà Lan, đậu nành hoặc đậu phụ ít có nguy cơ bị vô sinh do các vấn đề liên quan đến buồng trứng.\n" +
                "\n" +
                "Trên thực tế, protein thực vật thường ít chất béo và năng lượng hơn so với protein nguồn gốc động vật nên việc đưa chúng vào khẩu phần ăn hàng ngày cũng là một cách tuyệt vời để duy trì cân nặng hợp lý.\n" +
                "\n" +
                "1.5 Các loại ngũ cốc\n" +
                "Một người phụ nữ đang có kế hoạch mang thai nên ăn càng nhiều thực phẩm giàu dinh dưỡng càng tốt và ngũ cốc nguyên hạt là một trong số những lựa chọn tuyệt vời. Theo nhiều nghiên cứu, chế độ ăn uống lành mạnh với ngũ cốc nguyên hạt có mối liên quan đến khả năng sinh sản của người phụ nữ. Các chuyên gia cũng khuyến cái nên sử dụng một nửa lượng ngũ cốc hàng ngày ở dạng nguyên hạt.\n" +
                "\n" +
                "Carbohydrate tinh chế trong mì trắng, mì ống và gạo trắng không trực tiếp làm giảm khả năng mang thai của phụ nữ nhưng chúng khiến cơ thể thiếu hụt nhiều chất dinh dưỡng quan trọng như chất xơ, một số vitamin nhóm B và sắt. Chúng cũng có liên quan đến hội chứng buồng trứng đa nang (PCOS), một trong những nguyên nhân hàng đầu dẫn đến vô sinh ở phụ nữ. Hội chứng buồng trứng đa nang là tình trạng mất cân bằng nội tiết tố có thể trở nên tồi tệ hơn khi nồng độ insulin trong máu tăng cao và carbohydrate tinh chế là nguyên nhân chính gây ra sự tăng đột biến của insulin này. Mark Leondires, chuyên gia sinh sản và là giám đốc y tế của Hiệp hội Y học Sinh sản Connecticut giải thích rằng khi phụ nữ mắc hội chứng buồng trứng đa nang ăn quá nhiều carbohydrate tinh chế sẽ khiến nồng độ insulin trong máu tăng cao, ảnh hưởng đến buồng trứng của phụ nữ.");
        newsFood2.setCategory(Const.FirebaseRef.NEWS_FOOD);
        newsFood2.setImageUrl("https://vinmec-prod.s3.amazonaws.com/images/20200619_101442_570037_dinh_duong_va_hoat_do.max-800x800.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_FOOD).child(id12).setValue(newsFood2);

        NewsModel newsFood3 = new NewsModel();
        String id13 = CommonUtil.generateUUID();
        newsFood3.setId(id13);
        newsFood3.setTitle("MÁCH BẠN CHẾ ĐỘ DINH DƯỠNG CHO BÀ BẦU ĂN CHUẨN THEO TỪNG THÁNG");
        newsFood3.setContent("Dù đang ở giai đoạn nào của hành trình mang thai, mẹ bầu đều nên tránh những thực phẩm không lành mạnh, gây hại cho sức khỏe của cả mẹ và thai nhi, như:\n" +
                "\n" +
                "Thịt tái, sống: Thịt tái, sống hoặc chưa nấu chín có thể chứa toxoplasma và một số loại vi khuẩn, ký sinh trùng gây nguy hiểm cho thai nhi.\n" +
                "\n" +
                "Khoai tây mọc mầm: Ăn khoai tây mọc mầm có thể khiến mẹ bầu đau bụng, nôn mửa, tiêu chảy trong trường hợp nhẹ và ngộ độc trong trường hợp nặng hơn.\n" +
                "\n" +
                "Sữa chưa được tiệt trùng: Sữa chưa được tiệt trùng có thể chứa nhiều vi khuẩn gây hại cho sức khỏe của mẹ và thai nhi, tiêu biểu như Listeria gây ngộ độc thức ăn.\n" +
                "\n" +
                "Chất kích thích: Rượu, bia, thuốc lá, cà phê… là những thứ mẹ bầu nên tránh xa dù ở bất cứ thời điểm nào trong quá trình mang thai.\n" +
                "\n" +
                "Một số rau quả gây nguy hiểm cho thai nhi: Trong thời kỳ mang thai, chế độ dinh dưỡng cho bà bầu phải hết sức lưu ý, bởi một số loại thực phẩm có thể gây nguy hiểm cho thai nhi, như đu đủ xanh, dứa, rau răm, rau má, rau sam, cam thảo…\n" +
                "\n" +
                "Hạn chế đồ ngọt: Quá nhiều đồ ngọt sẽ khiến cho mẹ bầu tăng cân không kiểm soát, dễ gây tiểu đường thai kỳ và tăng nguy cơ sinh mổ. Do đó, dù không nhất thiết phải kiêng tuyệt đối đồ ngọt nhưng trong chế độ dinh dưỡng cho bà bầu thì đồ ngọt nên được hạn chế.");
        newsFood3.setCategory(Const.FirebaseRef.NEWS_FOOD);
        newsFood3.setImageUrl("http://preiq.vn/wp-content/uploads/2019/06/preiq-nen-co-che-do-dinh-duong-hop-ly-trong-giai-doan-tam-ca-nguyet-thu-hai-e1561173806278.png");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_FOOD).child(id13).setValue(newsFood3);

        NewsModel newsFood4 = new NewsModel();
        String id14 = CommonUtil.generateUUID();
        newsFood4.setId(id14);
        newsFood4.setTitle("BÀ BẦU THỪA DHA CÓ SAO KHÔNG? NHỮNG ĐIỀU BÀ BẦU CẦN BIẾT VỀ DHA");
        newsFood4.setContent("Cung cấp đầy đủ các dưỡng chất thiết yếu trong giai đoạn ba tháng đầu thai kỳ sẽ giúp làm giảm thiểu đáng kể tình trạng sinh non hoặc các tai biến tiền sản. Bên cạnh đó còn ảnh hưởng tích cực tới sức khỏe và sự phát triển của thai nhi sau này.\n" +
                "\n" +
                "Trong ba tháng giữa thời kỳ, mẹ bầu cần bổ sung một lượng DHA lớn do đây là giai đoạn tế bào não của trẻ phát triển mạnh mẽ. Trung bình mỗi phút có từ 250 – 1000 tế bào thần kinh mới. Cung cấp DHA cho mẹ bầu tại thời điểm này sẽ giúp làm tăng khả năng vận chuyển thông tin giữa các tế bào thần kinh. Ở giai đoạn này, tốt nhất bà bầu nên sử dụng chế độ ăn tuân thủ nguyên tắc một phần đạm, ba phần béo và sáu phần bột đường, cũng như cung cấp đầy đủ lượng DHA cần thiết cho cơ thể để bé có sự phát triển trí não toàn diện.\n" +
                "\n" +
                "Ở ba tháng cuối thai kỳ, não bộ và kích thước thai nhi phát triển rất nhanh. Do đó cần một lượng DHA lớn để phát triển toàn diện mạch máu và hệ thần kinh của trẻ. Mẹ bầu không cần tăng khẩu phần ăn như ba tháng giữa thời kỳ nhưng vẫn cần thực hiện chế độ ăn uống hợp lý, đủ chất. Bổ sung DHA trong giai đoạn này không những tốt cho bé mà còn giúp mẹ ngăn ngừa nguy cơ gặp các tai biến tiền sản, sinh non.\n" +
                "\n" +
                "Bổ sung DHA cho mẹ bầu như thế nào\n" +
                "\n" +
                "DHA là dưỡng chất thuộc nhóm Omega 3, do đó cơ thể mẹ không thể tự tổng hợp được mà cần thông qua các thực phẩm hay viên uống bổ sung.\n" +
                "\n" +
                "Trong lòng đỏ trứng gà có chứa lượng DHA rất lớn, vô cùng tốt cho sự phát triển của thai nhi. Trứng gà cũng là thực phẩm giá rẻ, dễ kiếm và dễ dàng chế biến thành nhiều loại thức ăn khác nhau để ttránh ngán cho mẹ như trứng rán, trứng sốt cà chua, trứng hấp,… tuy nhiên mẹ bầu nên lưu ý không được ăn trứng sống hay trứng lòng đào, chỉ ăn trứng khi đã nấu chín để đảm bảo vệ sinh an toàn thực phẩm.\n" +
                "\n" +
                "Mẹ bầu cũng nên sử dụng các loại cá biển như cá ngừ, cá thu, cá hồi do các loại cá này rất nhiều DHA, phục vụ cho sự phát triển trí não của trẻ. Chỉ nên ăn cá biển với một lượng vừa đủ khoảng ba lạng mỗi tuần, không nên ăn quá nhiều để tránh nhiễm độc thủy ngân. Đây là nhiễm độc vô cùng nguy hiểm trong khi mang thai do có thể gây ra tình trạng sinh non, sảy thai hoặc tử vong.\n" +
                "\n" +
                "Ngoài ra, các loại hạt, rau có màu xanh hoặc những chế phẩm từ sữa cũng là nguồn thực phẩm cung cấp DHA dồi dào cho bà bầu trong quá trình mang thai. Nếu như thường xuyên gặp phải tình trạng ốm nghén hoặc cảm thấy chế độ dinh dưỡng không cung cấp đủ lượng DHA cần thiết cho thai nhỉ, bà bầu có thể sử dụng một số thực phẩm chức năng để bổ sung đủ DHA cho bé phát triển toàn diện.");
        newsFood4.setCategory(Const.FirebaseRef.NEWS_FOOD);
        newsFood4.setImageUrl("http://preiq.vn/wp-content/uploads/2020/11/bo-sung-dha.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_FOOD).child(id14).setValue(newsFood4);

        NewsModel newsFood5 = new NewsModel();
        String id15 = CommonUtil.generateUUID();
        newsFood5.setId(id15);
        newsFood5.setTitle("Bà bầu không nên ăn khoai tây chiên");
        newsFood5.setContent("Thai phụ ăn nhiều khoai tây chiên có thể tăng lượng axit linoleic, gây hại cho bản thân và em bé trong bụng. ");
        newsFood5.setCategory(Const.FirebaseRef.NEWS_FOOD);
        newsFood5.setImageUrl("https://i1-suckhoe.vnecdn.net/2019/07/23/700173328h-1563853071-15638530-3719-6904-1563853377.png?w=500&h=300&q=100&dpr=1&fit=crop&s=ajQj4FQKKVWMvjgL8N66Dg");

        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).child(Const.FirebaseRef.NEWS_FOOD)
                .child(id15).setValue(newsFood5);

        NewsModel newsModel3 = new NewsModel();
        String id3 = CommonUtil.generateUUID();
        newsModel3.setId(id3);
        newsModel3.setTitle("Mẹ trở dạ trên trời, con được miễn phí bay 21 năm");
        newsModel3.setContent("Hãng hàng không quyết định em bé sinh trên máy bay sẽ được sử dụng dịch vụ miễn phí trọn đời. ");
        newsModel3.setCategory(Const.FirebaseRef.NEWS_BABY);
        newsModel3.setImageUrl("https://i1-dulich.vnecdn.net/2017/06/20/turkishbabycturkishairlines-1497945712.jpg?w=500&h=300&q=100&dpr=1&fit=crop&s=bKPAzA2efziGd90lTI6oWg");

        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).child(Const.FirebaseRef.NEWS_BABY).child(id3).setValue(newsModel3);

        NewsModel newsModel31 = new NewsModel();
        String id31 = CommonUtil.generateUUID();
        newsModel31.setId(id31);
        newsModel31.setTitle("Chăm sóc trẻ sơ sinh đến khi đầy tháng");
        newsModel31.setContent("7 ngày đầu sau sinh là khoảng thời gian khá quan trọng đối với một đứa trẻ sơ sinh chưa đầy tháng vì đây vẫn còn là thời kỳ chu sinh của trẻ và khả năng trẻ bị tử vong nếu không được chăm sóc đúng cách là rất cao (chiếm 50%). Trong giai đoạn này, thần kinh sọ não của trẻ bị ức chế vì ngủ nhiều, trẻ chỉ thức dậy khi đói hoặc ướt tã, do đó, cha mẹ cần có sự quan tâm, chăm sóc đặc biệt.\n" +
                "\n" +
                "Cách chăm sóc trẻ sơ sinh dưới 1 tháng tuổi là cần phải giữ ấm cơ thể trẻ, nếu để trẻ bị rét, hạ thân nhiệt trong thời gian dài sẽ tạo điều kiện cho vi khuẩn tấn công trẻ và gây ra nhiều bệnh. Nếu không có vấn đề gì xảy ra với mẹ và trẻ sau khi sinh thì tốt nhất hãy để trẻ được nằm chung với mẹ, điều này vừa giúp kết nối tình mẫu tử, vừa giúp truyền hơi ấm từ mẹ sang con và mẹ có thể quan sát con mọi lúc, kịp thời xử lý khi có vấn đề không mong muốn xảy ra.\n" +
                "\n" +
                "Khi còn trong bụng mẹ, trẻ được cung cấp chất dinh dưỡng liên tục từ máu mẹ qua nhau thai, chính vì thế khi chào đời trẻ dễ bị đói, rét nên cần phải có đủ năng lượng để sưởi ấm cơ thể và chống đỡ với thời tiết bên ngoài. Khi chăm sóc trẻ sơ sinh dưới 1 tuần tuổi cần phải nhớ rằng trẻ có nhu cầu ăn rất cao, cần được bú mẹ càng sớm càng tốt ngay khi chào đời nên mẹ phải đáp ứng nhu cầu của trẻ ngay khi bé cần chứ không nên tuân theo một giờ giấc nhất định nào.\n" +
                "\n" +
                "Sữa non là thức ăn chính và tốt nhất cho sự phát triển của trẻ trong giai đoạn này. Khoa học đã chứng minh, chất IgA có trong sữa mẹ 7 ngày đầu có chứa hàm lượng cao gấp nghìn lần so với sữa thường và có tới 4.000 bạch cầu trong 1cm3 sữa non có khả năng giúp trẻ tiêu diệt các vi khuẩn đường ruột. Chính vì vậy, mẹ không nên vắt sữa non bỏ đi mà hãy tận dụng triệt để cho trẻ ăn. Trẻ sơ sinh chưa đầy tháng nếu được bú sữa non ngay sau khi sinh thì tỷ lệ bị viêm phổi và tiêu chảy sẽ rất thấp.\n" +
                "\n" +
                "Một số biểu hiện sinh lý bình thường cũng rất thường gặp ở trẻ sơ sinh chưa chưa đầy tháng như: đi ngoài phân su, phân có màu xanh thẫm hoặc không mùi, đặc quánh... Tuy nhiên, nếu quá 2 ngày mà không thấy trẻ đi ngoài phân su, giảm cân hay vàng da, thường xuyên bị sặc khi bú, khó thở, tím tái, cứng hàm, khóc nhiều, ngủ li bì là biểu hiện không bình thường, cha mẹ cần đưa trẻ đến bệnh viện kiểm tra tình trạng sức khỏe để có hướng xử lý kịp thời. Trong trường hợp đầu của trẻ có bướu huyết thanh thì cần theo dõi chứ không nên chọc hút vì có thể bị khiến trẻ bị nhiễm khuẩn gây nguy hiểm.\n" +
                "\n" +
                "Ngoài ra, khi chăm sóc trẻ sơ sinh dưới 1 tuần tuổi bị nhẹ cân, thiếu tháng nhưng không có các dấu hiệu bất thường thì cần theo dõi ở cơ sở y tế đến khi bác sĩ đồng ý cho xuất viện. Khi về nhà cần theo dõi và chăm sóc trẻ cẩn thận.");
        newsModel31.setCategory(Const.FirebaseRef.NEWS_BABY);
        newsModel31.setImageUrl("https://vinmec-prod.s3.amazonaws.com/images/20190809_115531_333759_tre-em.max-800x800.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_BABY).child(id31).setValue(newsModel31);

        NewsModel newsModel32 = new NewsModel();
        String id32 = CommonUtil.generateUUID();
        newsModel32.setId(id32);
        newsModel32.setTitle("Hướng dẫn vệ sinh, chăm sóc trẻ sinh non");
        newsModel32.setContent("rẻ sinh non là trẻ sinh ra khi chưa được 37 tuần tuổi và có cân nặng dưới 37 tuần tuổi, cũng có trường hợp trẻ sinh non 34 tuần tuổi. Trẻ ra đời với tuổi thai nhỏ, các cơ quan chưa hoàn thiện về mặt giải phẫu và chức năng nên thường có nguy cơ mắc các biến chứng nguy hiểm.\n" +
                "\n" +
                "Trẻ sinh non xuất viện cần đáp ứng một số tiêu chuẩn như:\n" +
                "\n" +
                "Cân nặng phải tăng tốt từ 15-20g/ngày;\n" +
                "Thân nhiệt của trẻ sinh non ổn định;\n" +
                "Da hồng hào, không có cơn ngừng thở;\n" +
                "Người chăm sóc trẻ sinh non tại nhà được hướng dẫn từ ăn uống, vệ sinh và theo dõi những dấu hiệu bất thường. Khi bé có dấu hiệu bất thường thì cần đến bệnh viện khám lại.\n" +
                "Trong quá trình chăm sóc trẻ sinh non tại nhà, cần lưu ý một số điểm: Vệ sinh da, vệ sinh mắt, cách tắm cho trẻ sơ sinh, trong đó:\n" +
                "\n" +
                "Nên tắm cho trẻ sơ sinh vào lúc đói, cách xa bữa ăn, nên kết hợp với massage để kích thích sự phát triển của trẻ, không tắm cho trẻ lúc no vì dễ bị nôn trớ. Các bác sĩ khuyến cáo nên tắm cho trẻ sơ sinh hàng ngày vì trẻ sinh non sức đề kháng yếu, việc vệ sinh cơ thể sạch sẽ đóng vai trò quan trọng. Nhiệt độ phòng khi tắm cho trẻ sơ sinh nên để khoảng 26-28 độ C, nhiệt độ nước từ 37-40 độ. Sau khi tắm nên lau khô cho trẻ và có thể sử dụng sản phẩm làm mềm da để tránh hiện tượng khô da.\n" +
                "Vệ sinh mắt cho trẻ sinh non hàng ngày, theo khuyến cáo nên vệ sinh ở 3 thời điểm: Khi trẻ thức dậy, sau khi tắm và trước khi đi ngủ.\n" +
                "Người chăm sóc trẻ sinh non tại nhà cần vệ sinh tay thật sạch sẽ trước khi chăm sóc cho trẻ. Ngoài ra, chăm sóc trẻ sinh non tại nhà có thể cho trẻ tắm nắng hàng ngày để con có đủ lượng canxi cần thiết cho quá trình phát triển. Việc chăm sóc trẻ sinh non tại nhà cần sự tỉ mỉ, cẩn thận cao nên người chăm sóc bắt buộc phải có kiến thức nhất định.");
        newsModel32.setCategory(Const.FirebaseRef.NEWS_BABY);
        newsModel32.setImageUrl("https://vinmec-prod.s3.amazonaws.com/images/20190805_091920_282673_20190328_113040_91778.max-800x800.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_BABY).child(id32).setValue(newsModel32);


        NewsModel newsModel33 = new NewsModel();
        String id33 = CommonUtil.generateUUID();
        newsModel33.setId(id33);
        newsModel33.setTitle("Tại sao trẻ sơ sinh trông rất buồn cười?");
        newsModel33.setContent("Đầu của trẻ sơ sinh có thể trông hơi méo hoặc hơi nhọn. Điều này xảy ra khi trẻ trải qua quá trình sinh nở kéo dài. Đầu của trẻ sẽ trở lại hình dạng ban đầu sau một hoặc hai tuần.\n" +
                "\n" +
                "Những trẻ sinh mổ không bị áp lực khi đi qua do quá trình sinh nở gây ra, vì vậy trẻ có lợi thế hơn về bộ phận ngoại hình. Đầu của trẻ trở nên đẹp và tròn vì, khuôn mặt của trẻ không bị sưng như nhiều như trẻ được sinh thường.\n" +
                "\n" +
                "Các điểm da mềm trên đầu trẻ được gọi là thóp của trẻ sơ sinh hay còn được gọi là cửa đình đầu. Đây là các khe thở hình tam giác trong hộp sọ được bao phủ bởi một lớp da dày. Thóp được phân ra thành 2 phần, một ở phía trước và một ở phía sau - cho phép xương sọ của trẻ bị nén trong quá trình sinh nở, và sau khi trẻ ra đời, thóp cho phép não bộ phát triển nhanh chóng.\n" +
                "\n" +
                "Thóp phía sau mất khoảng sáu tháng để khép kín. Thóp phía trước mất từ \u200B\u200B12 đến 24 tháng để khép kín.\n" +
                "\n" +
                "Da đầu của trẻ sơ sinh thường xuất hiện cứt trâu đỏ và bong tróc. Nó thường biến mất trong một vài tuần hoặc vài tháng và hiếm khi gây khó chịu hoặc ngứa. Nếu bạn nhận thấy có cứt trâu trên đầu bé, hãy thử gội đầu thường xuyên hơn bằng dầu gội cho bé và sử dụng bàn chải lông mềm. Không nên sử dụng dầu gội thảo dược khi chưa tham khảo ý kiến với bác sĩ của bé vì chúng có thể gây kích ứng làn da nhạy cảm và mềm mại của trẻ.");
        newsModel33.setCategory(Const.FirebaseRef.NEWS_BABY);
        newsModel33.setImageUrl("https://vinmec-prod.s3.amazonaws.com/images/20200728_082442_097312_loi-khuyen-de-co-cuoc.max-800x800.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_BABY).child(id33).setValue(newsModel33);

        NewsModel newsModel34 = new NewsModel();
        String id34 = CommonUtil.generateUUID();
        newsModel34.setId(id34);
        newsModel34.setTitle("Giúp trẻ tắm an toàn");
        newsModel34.setContent("Khuyến cáo thời gian tắm lần đầu tiên của bé đã thay đổi trong vài năm qua. Trong khi hầu hết tại các cơ sở y tế thường tắm cho trẻ sơ sinh trong vòng một hoặc hai giờ sau khi sinh, tuy nhiên nhiều cơ sở đang thay đổi quy trình này.\n" +
                "\n" +
                "Tổ chức Y tế Thế giới (WHO) khuyến cáo nên trì hoãn việc tắm đầu tiên cho trẻ sơ sinh đến 24 giờ sau khi sinh hoặc đợi ít nhất 6 giờ vì lý do văn hóa.\n" +
                "\n" +
                "Dưới đây là một số lý do tại sao chúng ta nên trì hoãn việc tắm đầu tiên cho bé:\n" +
                "\n" +
                "Nhiệt độ cơ thể và lượng đường trong máu: Trẻ tắm ngay sau sinh có thể dễ bị nhiễm lạnh và hạ thân nhiệt. Sự căng thẳng nhỏ diễn ra khi tắm sớm cũng có thể khiến một số trẻ dễ bị tụt đường huyết.\n" +
                "Mối liên kết và cho con bú: Việc cho trẻ đi tắm quá sớm có thể làm gián đoạn quá trình chăm sóc da kề da, gắn kết mẹ con và việc nuôi con bằng sữa mẹ sớm. Một nghiên cứu đã cho thấy tỷ lệ thành công trong việc cho con bú sữa mẹ tại bệnh viện tăng 166% sau khi thực hiện trì hoãn 12 giờ trong lần tắm đầu tiên của trẻ so với những trẻ được tắm trong vài giờ đầu tiên.\n" +
                "Da khô: Vernix hay được gọi là chất gây, một chất màu trắng như sáp phủ lên da em bé trước khi sinh, chất này hoạt động như một loại kem dưỡng ẩm tự nhiên và có thể có đặc tính chống vi khuẩn. Theo Học viện Nhi khoa Hoa Kỳ (AAP), tốt nhất nên để chất gây trên da trẻ sơ sinh trong một thời gian để giúp làn da mỏng manh của trẻ không bị khô. Điều này đặc biệt quan trọng đối với sinh non vì da của trẻ rất dễ bị tổn thương.");
        newsModel34.setCategory(Const.FirebaseRef.NEWS_BABY);
        newsModel34.setImageUrl("https://vinmec-prod.s3.amazonaws.com/images/20190824_041527_261629_tam-cho-tre.max-1800x1800.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_BABY).child(id34).setValue(newsModel34);

        NewsModel newsModel2 = new NewsModel();
        String id2 = CommonUtil.generateUUID();
        newsModel2.setId(id2);
        newsModel2.setTitle("LỐI SỐNG LÀNH MẠNH CHO MỘT THAI KỲ KHỎE MẠNH");
        newsModel2.setContent("Ăn đúng cách\n" +
                "\n" +
                "Thực phẩm là nguồn dinh dưỡng và năng lượng chính cho bạn và bé. Khi bé ngày càng phát triển, bạn sẽ cần nhiều năng lượng và dưỡng chất hơn. Nhưng việc ăn gấp đôi khối lượng (nói cách khác là ăn cho 2 người) không còn được xem là chế độ dinh dưỡng hợp lý đối với phụ nữ mang thai. Nhiều chuyên gia nhấn mạnh tầm quan trọng của việc ăn uống lành mạnh; giữ cân nặng phù hợp và hoạt động đều đặn giúp tối đa cơ hội “mẹ tròn con vuông”.\n" +
                "\n" +
                "Một số nguyên tắc chính để có một chế độ ăn uống lành mạnh:\n" +
                "\n" +
                "Ăn trái cây, rau quả cùng ngũ cốc, protein theo tỷ lệ 1:1 trong bữa ăn\n" +
                "Đa dạng nguồn protein. Thực phẩm chứa protein bao gồm thịt, cá, các loại đậu, hạt và trứng.\n" +
                "Ăn một lượng nhỏ các sản phẩm từ sữa như sữa, phô mai, yaourt mỗi bữa ăn. Uống sữa ít béo (1%) thay vì sữa nguyên chất.\n" +
                "Đảm bảo ít nhất một nữa ngũ cốc bạn ăn là ngũ cốc nguyên hạt. Ngũ cốc nguyên hạt chứa toàn bộ dinh dưỡng của hạt ngũ cốc, trái ngược với các hạt ngũ cốc tinh chế đã được xử lý loại bỏ các chất xơ. Thực phẩm ngũ cốc nguyên hạt bao gồm gạo nứt, lúa mì và bột yến mạch. Đọc kỹ thành phần trên các bao bì thực phẩm trước khi sử dụng.\n" +
                "Hạn chế sử dụng các thực phẩm nhiều chất béo, dầu mỡ, đường và muối\n" +
                " \n" +
                "\n" +
                "Tập thể dục đều đặn\n" +
                "\n" +
                "Để có một sức khoẻ tốt cần luyện tập thể dục thường xuyên đặc biệt là trong suốt thời gian mang thai. Phụ nữ mang thai nên tập thể dục ít nhất 30 phút mỗi ngày. Hình thức và khối lượng tập an toàn cho bạn trong suốt thai kỳ phụ thuộc vào sức khoẻ và tần suất hoạt động của bạn trước khi mang thai.\n" +
                "\n" +
                "Thật tuyệt nếu bạn đã có tập thể dục đều đặn trước khi mang thai. Nếu bạn mới bắt đầu thì có thể làm quen trước với hoạt động đi bộ, bơi hoặc đạp xe. Đi bộ nhanh là một hình thức hoạt động đơn giản không tốn nhiều chi phí. Đây cũng là một phương pháp tốt để giảm cân. Nếu bạn không thường xuyên tập thể dục, hãy thảo luận với bác sĩ để có một phương án tập luyện an toàn và thực hiện nó từng chút một.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Sử dụng axit folic\n" +
                "\n" +
                "Sử dụng axit folic (hay còn gọi là folate) là điều cần thiết trong giai đoạn trước và trong khi mang thai. Một nghiên cứu chỉ ra rằng dùng 0.4 mg vitamin B ít nhất trong 1 tháng trước và trong khi mang thai làm giảm nguy cơ bé bị khiếm khuyết về não và xương sống hay còn gọi là khiếm khuyết ống thần kinh. Mặc dù axit folic được tìm thấy trong nhiều loại thực phẩm và có ở bánh mì, mì ống và ngũ cốc nhưng khó có thể đạt được 0.4mg mỗi ngày chỉ từ các thực phẩm này. Đây chính là lý do, phụ nữ có dự định mang thai nên uống bổ sung 0.4 mg axit folic mỗi ngày. Bạn có thể uống một loại vitamin cho phụ nữ trước mang thai có chứa hàm lượng axit folic nêu trên.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Duy trì cân nặng phù hợp\n" +
                "\n" +
                "Để có được sức khỏe tốt, bạn nên giữ cân nặng ở mức phù hợp nhất với chiều cao của bạn. Chỉ số khối cơ thể (BMI) được tính dựa vào chiều cao và cân nặng, xác định bạn có nhẹ cân, cân nặng bình thường, thừa cân hoặc béo phì hay không.\n" +
                "\n" +
                "BMI dưới 18,5 là thiếu cân; 18.5-24.9 là bình thường; và 25-29,9 là thừa cân. Một người có BMI từ 30 trở lên là béo phì. Thừa cân hoặc béo phì có thể gây ra các vấn đề trong quá trình mang thai, vì vậy nên giữ mức cân nặng bình thường.\n" +
                "\n" +
                "Nếu bạn bị thiếu cân, bạn nên cố gắng tăng cân bằng cách bổ sung nhiều calo mỗi ngày hơn. Bạn có thể thêm vào các bữa ăn nhiều thức ăn có hàm lượng calo cao như các loại hạt, yến mạch, thực phẩm bổ sung, sinh tố trái cây và sữa chua.\n" +
                "\n" +
                "Nếu thừa cân và béo phì, bạn nên cắt giảm lượng calo mỗi ngày bằng việc vận động nhiều. Có hai cách dễ dàng để giảm lượng calo là tránh thức uống có đường và thức ăn có hàm lượng chất béo cao. Kiểm soát được khẩu phần ăn chính là chìa khóa để giảm cân.\n" +
                "\n" +
                "Tập thể dục sẽ giúp bạn giảm cân. Hầu hết những người đã giảm cân đều tập thể dục với cường độ vừa phải từ 60-90 phút mỗi ngày trong tuần. Hoạt động cường độ vừa phải là hoạt động bạn vẫn có thể nói chuyện, nhưng không thể hát trong lúc thực hiện. Bạn có thể giảm thời gian tập thể dục bằng cách tăng thêm các hoạt động mạnh. Hoạt động mạnh là hoạt động làm tăng nhịp tim và làm bạn khó nói chuyện ví dụ như chạy bộ, nhảy dây, bơi. Bạn không cần phải tập một lần 60 – 90 phút mà có thể tập 20-30 phút, ba lần mỗi ngày.\n" +
                "\n" +
                "Chắc chắn rằng bạn được sự đồng ý từ bác sĩ trước khi bắt đầu một chương trình thể dục nào đó nếu bạn thừa cân hoặc béo phì. Bạn có thể tham khảo chương trình tập luyện từ\u200B huấn luyện viên thể chất tại phòng tập thể dục hoặc câu lạc bộ sức khỏe.\n" +
                "\n" +
                "Một số người có thể khó giảm cân bằng chế độ ăn uống và tập thể dục một mình. Nếu bạn có chỉ số BMI từ 30 trở lên, hoặc tối thiểu là 27 và có những tiền sử bệnh án nhất định, như tiểu đường hoặc tim mạch thì có thể dùng thuốc để giúp giảm cân. Những loại thuốc này nên được kết hợp với một kế hoạch ăn uống lành mạnh và hoạt động thể chất thường xuyên.\n" +
                "\n" +
                "Phẫu thuật giảm cân có thể là lựa chọn cho những người béo phì (chỉ số BMI từ 40 trở lên) hoặc có chỉ số BMI từ 35 đến 39 kèm những rắc rối với sức khỏe do béo phì gây ra. Phẫu thuật có thể giúp bạn giảm cân đáng kể, giúp làm giảm nguy cơ của các vấn đề về sức khỏe nghiêm trọng liên quan đến bệnh béo phì.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Ngừng sử dụng những chất không lành mạnh\n" +
                "\n" +
                "Hút thuốc, uống rượu, bia và sử dụng các chất ma túy (bạch phiến, cocaine, ma túy đá, cần sa) trong thai kỳ có thể ảnh hưởng nghiêm trọng và lâu dài đến bé như là dị tật bẩm sinh, nhẹ cân, non tháng. Việc sử dụng tùy tiện các thuốc theo toa như oxycodone mà không được sự hướng dẫn của bác sĩ của gây hậu quả tương tự.\n" +
                "\n" +
                "Tốt nhất là bạn nên từ bỏ hoàn toàn các chất này từ khi bạn có ý định mang thai cho đến suốt thai kỳ.\n" +
                "\n" +
                "Các bác sĩ có thể hướng dẫn bạn những cách để vượt qua giai đoạn đầu hoặc giới thiệu bạn đến nhưng cơ sở điều trị phù hợp.\n" +
                "\n" +
                "Chồng bạn cũng nên từ bỏ những chất này. Nhiều trường hợp đã chỉ ra rằng hút thuốc và lạm dụng thuốc sẽ làm giảm khả năng sinh sản và chất lượng tinh trùng. Sống cùng với người hút thuốc đồng nghĩa với việc bạn sẽ hít khói thuốc một cách gián tiếp (hút thuốc thụ động). Việc hít phải khói thuốc có chứa hóa chất độc hại sẽ ảnh hưởng xấu đến sức khỏe của bạn và cả sự phát triển của thai nhi. Hút thuốc thụ động trong quá trình mang thai làm tăng nguy cơ trẻ mắc hội chứng đột tử (SIDS) và trẻ nhẹ cân.\n" +
                "\n" +
                "Nếu chồng bạn và các đồng nghiệp chưa sẳn sàng để từ bỏ thuốc lá, hãy đề nghị họ hút thuốc bên ngoài, và không đồng ý cho bất kỳ ai hút thuốc trong nhà của bạn\n" +
                "\n" +
                " \n" +
                "\n" +
                "Giữ môi trường xung quanh bạn an toàn\n" +
                "\n" +
                "Các loại hoá chất có mặt khắp nơi, trong không khí, nước, đất, thực phẩm và sản phẩm bạn sử dụng. Trước và trong khi mang thai, bạn có thể tiếp xúc với các loại hoá chất này ở nơi làm việc, ở nhà hoặc nơi công cộng.\n" +
                "\n" +
                "Một số loại hoá chất có thể ảnh hưởng đến sự phát triển của thai nhi. Hiện nay, những ảnh hưởng của hóa chất cho phụ nữ mang thai chưa được biết hết. Nhiều hoá chất được tìm thấy ở nhà và nơi làm việc có thể làm cho bạn khó mang thai.\n" +
                "\n" +
                "Những phụ nữ làm việc ở nông trại, nhà máy, tiệm giặt sấy, lĩnh vực điện tử hay in ấn hoặc có sở thích vẽ tranh hay làm đồ gốm nên trao đổi với bác sĩ toàn bộ các chất có khả năng gây hại cho bản thân.");
        newsModel2.setCategory(Const.FirebaseRef.NEWS_MOM);
        newsModel2.setImageUrl("https://hongngochospital.vn/wp-content/uploads/2020/03/thoi-quen-xau-khi-mang-thai-4-scaled-1000x697.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_MOM).child(id2).setValue(newsModel2);

        NewsModel newsModel21 = new NewsModel();
        String id21 = CommonUtil.generateUUID();
        newsModel21.setId(id21);
        newsModel21.setTitle("Mẹ bầu đừng buồn và giận dữ khi mang thai vì em bé sẽ chịu những hậu quả không ngờ");
        newsModel21.setContent("Con bạn sẽ nhận các tín hiệu stress từ mẹ\n" +
                "\n" +
                " Trong quá trình thai nhi lớn lên, con liên tục nhận được các tín hiệu từ mẹ. Đó không chỉ là âm thanh nhịp tim của bạn hay bất kỳ bản nhạc nào bạn nghe, mà còn nhận được các tín hiệu hóa học qua nhau thai. Những thay đổi tiêu cực trong tâm lý thai phụ sẽ đưa đến sự gia tăng các stress hormone. Thông qua bánh nhau, các hormone này cũng tăng lên trong máu thai nhi khiến chúng gặp phải những căng thẳng tương tự.\n" +
                "\n" +
                "Trục nội tiết hạ đồi-tuyến yên-thượng thận được cho là có vai trò trong việc điều hoà cảm xúc ở người. Tâm lý tiêu cực kéo dài dẫn đến tình trạng rối loạn điều hoà trục hạ đồi-tuyến yên-thượng thận. Biểu hiện bằng tình trạng tăng cortisol trong máu, dẫn đến tăng huyết áp và nhịp tim. Trong thai kì, bánh nhau cũng sản xuất các chất kích thích hoạt động của trục này. 10-20% cortisol qua nhau thai, nếu cortisol tăng lâu dài sẽ gây ảnh hưởng không tốt đến quá trình phát triển não bộ của thai nhi.\n" +
                "\n" +
                "\n" +
                "Cảm xúc tiêu cực của mẹ bầu khiến thai nhi có thể bị chậm tăng trưởng (suy dinh dưỡng) trong tử cung\n" +
                "\n" +
                "Các nghiên cứu còn cho thấy những cảm xúc tiêu cực gây ra tăng trở kháng động mạch tử cung, khiến dòng máu đến nuôi bào thai bị giảm. Hậu quả là thai nhi có thể bị chậm tăng trưởng trong tử cung và nguy cơ mẹ bị tiền sản giật.\n" +
                "\n" +
                "Căng thẳng làm tăng nguy cơ viêm âm đạo trong thai kỳ\n" +
                "\n" +
                "Căng thẳng cũng gây ra những thay đổi về hệ vi sinh vật của cơ thể, trong đó có hệ vi sinh vật âm đạo. Những thai phụ thường xuyên gặp phải căng thẳng có tăng nguy cơ viêm âm đạo do vi khuẩn và nấm. Phổ vi sinh vật này có khả năng lây truyền dọc cho thai ở thời điểm chuyển dạ.");
        newsModel21.setCategory(Const.FirebaseRef.NEWS_MOM);
        newsModel21.setImageUrl("https://tudu.com.vn/data/image/2020/10-2020/780b6f91a9a657f80eb7.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_MOM).child(id21).setValue(newsModel21);

        NewsModel newsModel22 = new NewsModel();
        String id22 = CommonUtil.generateUUID();
        newsModel22.setId(id22);
        newsModel22.setTitle("Mẹ bầu đừng buồn và giận dữ khi mang thai vì em bé sẽ chịu những hậu quả không ngờ");
        newsModel22.setContent("Con bạn sẽ nhận các tín hiệu stress từ mẹ\n" +
                "\n" +
                " Trong quá trình thai nhi lớn lên, con liên tục nhận được các tín hiệu từ mẹ. Đó không chỉ là âm thanh nhịp tim của bạn hay bất kỳ bản nhạc nào bạn nghe, mà còn nhận được các tín hiệu hóa học qua nhau thai. Những thay đổi tiêu cực trong tâm lý thai phụ sẽ đưa đến sự gia tăng các stress hormone. Thông qua bánh nhau, các hormone này cũng tăng lên trong máu thai nhi khiến chúng gặp phải những căng thẳng tương tự.\n" +
                "\n" +
                "Trục nội tiết hạ đồi-tuyến yên-thượng thận được cho là có vai trò trong việc điều hoà cảm xúc ở người. Tâm lý tiêu cực kéo dài dẫn đến tình trạng rối loạn điều hoà trục hạ đồi-tuyến yên-thượng thận. Biểu hiện bằng tình trạng tăng cortisol trong máu, dẫn đến tăng huyết áp và nhịp tim. Trong thai kì, bánh nhau cũng sản xuất các chất kích thích hoạt động của trục này. 10-20% cortisol qua nhau thai, nếu cortisol tăng lâu dài sẽ gây ảnh hưởng không tốt đến quá trình phát triển não bộ của thai nhi.\n" +
                "\n" +
                "\n" +
                "Cảm xúc tiêu cực của mẹ bầu khiến thai nhi có thể bị chậm tăng trưởng (suy dinh dưỡng) trong tử cung\n" +
                "\n" +
                "Các nghiên cứu còn cho thấy những cảm xúc tiêu cực gây ra tăng trở kháng động mạch tử cung, khiến dòng máu đến nuôi bào thai bị giảm. Hậu quả là thai nhi có thể bị chậm tăng trưởng trong tử cung và nguy cơ mẹ bị tiền sản giật.\n" +
                "\n" +
                "Căng thẳng làm tăng nguy cơ viêm âm đạo trong thai kỳ\n" +
                "\n" +
                "Căng thẳng cũng gây ra những thay đổi về hệ vi sinh vật của cơ thể, trong đó có hệ vi sinh vật âm đạo. Những thai phụ thường xuyên gặp phải căng thẳng có tăng nguy cơ viêm âm đạo do vi khuẩn và nấm. Phổ vi sinh vật này có khả năng lây truyền dọc cho thai ở thời điểm chuyển dạ.");
        newsModel22.setCategory(Const.FirebaseRef.NEWS_MOM);
        newsModel22.setImageUrl("https://tudu.com.vn/data/image/2020/10-2020/780b6f91a9a657f80eb7.jpg");
        FirebaseDatabase.getInstance().getReference(Const.FirebaseRef.NEWS).
                child(Const.FirebaseRef.NEWS_MOM).child(id22).setValue(newsModel22);
    }

}
