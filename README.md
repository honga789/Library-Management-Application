# Phát triển ứng dụng Java quản lý thư viện

## Xây dựng giao diện người dùng
- Sử dụng JavaFX để tạo giao diện.
-  Danh sách tài liệu: Hiển thị tất cả tài liệu trong thư viện.
-  Thông tin tài liệu: Hiển thị chi tiết khi chọn một cuốn tài liệu.
-  Chức năng quản lý: Nút hoặc menu để thêm, xóa, sửa tài liệu và thành viên.
-  Mượn/Trả tài liệu: Giao diện cho phép thành viên mượn hoặc trả tài liệu.

## Quản lý tài liệu
- Thêm tài liệu: Thêm tài liệu mới vào danh sách thư viện.
- Xóa tài liệu: Xóa tài liệu khỏi danh sách khi không còn cần thiết.
- Sửa tài liệu: Chỉnh sửa thông tin tài liệu, như số lượng hoặc tác giả.
- Tìm kiếm tài liệu: Tìm kiếm tài liệu theo tên, tác giả, hoặc thể loại.
## Quản lý người dùng thư viện
- Mượn tài liệu: Kiểm tra điều kiện trước khi cho mượn, như số lượng tài liệu có sẵn.
- Trả tài liệu: Cập nhật trạng thái sau khi trả tài liệu.
- Thông tin thành viên: Quản lý thông tin cá nhân và trạng thái mượn tài liệu.

## Xử lý các trường hợp lỗi, như kiểm tra điều kiện khi mượn tài liệu
- Kiểm tra điều kiện khi mượn tài liệu: Ví dụ, không cho phép mượn tài
liệu nếu không có sẵn.
- Thông báo lỗi: Cung cấp thông báo rõ ràng khi xảy ra lỗi, như nhập sai
thông tin.

## Tích hợp API tra cứu thông tin tài liệu
- Sử dụng API như Google Books API để tự động lấy thông tin tài liệu
dựa trên ISBN hoặc tiêu đề.

## Sử dụng đa luồng
- Thực hiện các tác vụ nặng như gọi API hoặc truy vấn cơ sở dữ liệu
trong luồng riêng.
- Đảm bảo giao diện người dùng không bị treo trong quá trình xử lý.


## Hệ thống đề xuất gợi ý tài liệu:
- Gợi ý tài liệu cho thành viên dựa trên lịch sử mượn hoặc thể loại yêu thích.
- Sử dụng thuật toán Collaborative Filtering


## Chức năng chính của ứng dụng

### Quản lý tài liệu (Thêm, Xóa, Sửa, Tìm kiếm) 1 Bắt buộc
### Quản lý người dùng thư viện (mượn/trả tài liệu, thông tin thành viên)

### Xử lý các trường hợp lỗi, như kiểm tra điều kiện khi mượn tài liệu

### Giao diện người dùng JavaFx

### Tích hợp API tra cứu thông tin tài liệu từ GoogleBookAPI

### Sử dụng đa luồng để cải thiện trải nghiệm người dùng

### Chức năng tự sáng tạo: Hệ thống đề xuất gợi ý tài liệu dùng Collaborative Filtering

### Sử dụng Design Pattern vào ứng dụng: Mô hình MVC với DAO (Data Access Object)
