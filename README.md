# Phát triển ứng dụng Java quản lý thư viện

Kiến trúc ứng dụng (mô hình MVC + DAO design partern), các lớp trong ứng dụng:
<img width="778" alt="Screenshot 2024-12-14 at 23 34 10" src="https://github.com/user-attachments/assets/5d0c7e11-643a-43b5-8ee1-2d685bc76a1b" />


## Xây dựng giao diện người dùng
- Sử dụng JavaFX để tạo giao diện.
-  Danh sách tài liệu: Hiển thị tất cả tài liệu trong thư viện.
-  Thông tin tài liệu: Hiển thị chi tiết khi chọn một cuốn tài liệu.
-  Chức năng quản lý: Nút hoặc menu để thêm, xóa, sửa tài liệu và thành viên.
-  Mượn/Trả tài liệu: Giao diện cho phép mượn hoặc trả tài liệu.

## Quản lý tài liệu
- Thêm tài liệu: Thêm tài liệu mới vào danh sách thư viện.
- Xóa tài liệu: Xóa tài liệu khỏi danh sách khi không còn cần thiết.
- Sửa tài liệu: Chỉnh sửa thông tin tài liệu, như số lượng hoặc tác giả.
- Tìm kiếm tài liệu: Tìm kiếm tài liệu theo tên.
## Quản lý người dùng thư viện
- Mượn tài liệu: Kiểm tra điều kiện trước khi cho mượn, như số lượng tài liệu có sẵn.
- Trả tài liệu: Cập nhật trạng thái sau khi trả tài liệu.
- Thông tin thành viên: Quản lý thông tin cá nhân và trạng thái mượn tài liệu.

## Xử lý các trường hợp lỗi, như kiểm tra điều kiện khi mượn tài liệu
- Kiểm tra điều kiện khi mượn tài liệu: Ví dụ, không cho phép mượn tài
liệu nếu không có sẵn.
- Thông báo lỗi: Cung cấp thông báo khi xảy ra lỗi, như nhập sai
thông tin.

## Tích hợp API tra cứu thông tin tài liệu
- Sử dụng API của Google Books API để tự động lấy thông tin tài liệu
dựa trên ISBN.

## Sử dụng đa luồng
- Thực hiện các tác vụ nặng như gọi API và truy vấn cơ sở dữ liệu
trong luồng riêng.
- Đảm bảo giao diện người dùng không bị treo trong quá trình xử lý.


## Hệ thống đề xuất gợi ý tài liệu:
- Gợi ý tài liệu cho thành viên dựa trên lịch sử mượn.
- Sử dụng thuật toán Collaborative Filtering.


## Chức năng chính của ứng dụng

### Quản lý tài liệu (Thêm, Xóa, Sửa, Tìm kiếm)
### Quản lý người dùng thư viện (mượn/trả tài liệu, thông tin thành viên)
### Xử lý các trường hợp lỗi, như kiểm tra điều kiện khi mượn tài liệu
### Giao diện người dùng JavaFx
### Tích hợp API tra cứu thông tin tài liệu từ GoogleBookAPI
### Sử dụng đa luồng để cải thiện trải nghiệm người dùng
### Chức năng tự sáng tạo: Hệ thống đề xuất gợi ý tài liệu dùng Collaborative Filtering
### Sử dụng Design Pattern vào ứng dụng: Mô hình MVC với DAO (Data Access Object)



#Thành viên:
1. Nguyễn Hồng Anh - 2302 1466
2. Nguyễn Đức Dũng - 2302 1494
3. Trần Ánh Duy - 2302 1506
4. Đậu Đức Hiếu - 2302 1546

