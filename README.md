# Library Management Application

## Giới thiệu

Library Management Application là một ứng dụng quản lý thư viện được phát triển bằng Java với kiến trúc **MVC mở rộng** và áp dụng các **design patterns** như DAO, Singleton, và Factory. Ứng dụng cung cấp các chức năng quản lý tài liệu, người dùng, và hỗ trợ các thành viên mượn/trả sách. Giao diện được thiết kế bằng **JavaFX** với SceneBuilder, đảm bảo tính trực quan và dễ sử dụng.

---

## Kiến trúc hệ thống

<img width="778" alt="Screenshot 2024-12-14 at 23 34 10" src="https://github.com/user-attachments/assets/5d0c7e11-643a-43b5-8ee1-2d685bc76a1b" />

### Mô hình MVC mở rộng

Ứng dụng sử dụng mô hình MVC kết hợp với các packages chính:

- **Models**: Đại diện cho dữ liệu của ứng dụng.
- **Views**: Giao diện hiển thị và tương tác với người dùng, sử dụng JavaFX.
- **Controllers**: Xử lý logic điều hướng và tương tác giữa View và các lớp khác.
- **Services**: Chứa logic nghiệp vụ của ứng dụng.
- **DAO**: Quản lý kết nối và thao tác dữ liệu từ cơ sở dữ liệu.
- **Utils**: Các tiện ích hỗ trợ (kết nối cơ sở dữ liệu, gọi API, xử lý đa luồng).

---

### Design Patterns

1. **DAO (Data Access Object)**:
   - Các lớp trong package `dao` chịu trách nhiệm truy xuất và thao tác dữ liệu với cơ sở dữ liệu.
   - Tách biệt logic xử lý dữ liệu khỏi các lớp khác, giúp dễ bảo trì và mở rộng.

2. **Singleton**:
   - Các lớp con của `Cache`, `services/SessionService`, `utils/API/ExecutorHandle` đều sử dụng Singleton để đảm bảo chỉ có một instance duy nhất trong ứng dụng.

3. **Factory + Reflection + Singleton**:
   - Dùng để tạo Singleton cho các lớp con của `Cache`. Mọi lớp kế thừa `Cache` tự động trở thành Singleton mà không cần khai báo thêm.

### Multithreading
  - Các tác vụ nặng như xử lý dữ liệu trong `syncdao` hoặc gọi API trong `utils/API` sử dụng đa luồng, đảm bảo hiệu năng.
  - Kết hợp callback để xử lý các tác vụ bất đồng bộ.

---

## Các chức năng chính

### Admin
- **Quản lý sách**: Thêm, sửa, xoá, tìm kiếm (theo title).
- **Quản lý người dùng**: Thêm, sửa, xoá, tìm kiếm (theo username).
- **Duyệt yêu cầu mượn/trả sách**:
  - Xử lý các lỗi mượn/trả sách: 
    - Sách đã hết hoặc bị xóa.
    - Sách bị xóa nhưng cần thêm lại khi trả.
  - Hiển thị thông báo lỗi qua alert/popup.
- **Tích hợp Google Book API**: Tự động thêm sách vào thư viện dựa trên ISBN.

### Member
- **Tìm kiếm sách**: Sử dụng Fulltext Index Search (MySQL).
- **Hệ thống gợi ý sách**:
  - Dựa trên thuật toán **Collaborative Filtering - Userbase** kết hợp thể loại sách (genre).
- **Quản lý mượn sách**:
  - Xem danh sách sách đã mượn, đang chờ duyệt, hoặc đã trả.

### Các chức năng khác
- **Giao diện**: Được thiết kế bằng JavaFX với SceneBuilder.
- **Bảo mật**: Mã hóa mật khẩu bằng SHA-256.
- **Unit Test**: Cài đặt kiểm thử với JUnit.

---

## Hướng dẫn cài đặt và chạy

### Yêu cầu hệ thống

1. **JDK**: Phiên bản 11 trở lên.
2. **Maven**: Để quản lý dependencies.
3. **JavaFX**: Bao gồm thư viện JavaFX runtime.
4. **SceneBuilder** (tùy chọn): Để chỉnh sửa giao diện.
  
*Hoặc sử dụng IntelliJ*

### Cài đặt

#### 1. **Clone dự án**
   - Mở terminal/command prompt và chạy lệnh sau:
     ```bash
     git clone https://github.com/honga789/Library-Management-Application.git
     cd Library-Management-Application
     cd LibApp
     ```

#### 2. **Cài đặt dependencies**
   - Dự án sử dụng Maven để quản lý dependencies. Đảm bảo Maven đã được cài đặt trên máy.
   - Tải và cài đặt dependencies bằng cách chạy:
     ```bash
     mvn clean install
     ```

#### 3. **Chạy ứng dụng**
   - Sử dụng Maven để khởi chạy ứng dụng:
     ```bash
     mvn javafx:run
     ```

### *Hoặc có thể chạy với IntelliJ (mở thư mục LibApp), mở pom.xml, refresh pom, chạy file MainApp*

---

## Thành viên

### 1. Nguyễn Hồng Anh - 2302 1466 (github: honga789)
   - **DAO**:
     - `BookDAO`, `BorrowRecordDAO`, `GenreTypeDAO`, `UserDAO`.
   - **Các lớp syncDao cụ thể**:
     - `BookSyncDAO`, `UserSyncDAO`, ...
   - **Admin Services**:
     - `BookService`, `BorrowRecordService`, `UserService`.
   - **Fulltext Index Search**:
     - `BookDAO`.
   - **Unit Test**:
     - Sử dụng `JUnit`.

### 2. Nguyễn Đức Dũng - 2302 1494 (github: xhatsu)
   - **Utils/API**:
     - `GoogleBookAPI`, `ImageAPI`.
     - Xử lý API trong đa luồng bằng `Executor` và callback.
     - API `Junit` test
   - **Xử lý tương tác người dùng và dữ liệu trong Admin Controllers**:
     - `AdminManageUserController`, `AdminManageDocumentController`, `AdminApproveRequestController`.
   - **Tự động tìm và ghi thông tin sách**:
     - Add book `AdminManageDocumentController` xử dụng `GoogleBookAPI`.
   - **`BookService`**:
     - Thu thập và quản lý `GenresType`
   - **Quản lý và thông báo lỗi**


### 3. Trần Ánh Duy - 2302 1506 (github: Duyacquy)
   - **Views**:
     - Thiết kế giao diện.
   - **Authen Controllers**:
     - `LoginController`, `RegisterController`.
   - **Truy xuất và hiển thị dữ liệu trong Member Controllers và Admin Controllers**:
     - `MemberBorrowedTabController`, `AdminManageUserController`, ...
   - **Authen Services**:
     - `LoginService`, `RegisterService`.
   - **Bảo mật mật khẩu**:
     - Thư viện mã hóa `PasswordService` (SHA-256).

### 4. Đậu Đức Hiếu - 2302 1546 (github: DauDucHieu)
   - **Kiến trúc hệ thống**:
     - Tổ chức thư mục, các file chính như `MainApp`, `MainAppController`.
   - **Member Controllers**:
     - `MemberViewController`, `MemberHomeTabController`.
   - **Kết nối cơ sở dữ liệu**:
     - `Utils/Database`.
   - **Recommendation Service**:
     - `RecommendationService`.
   - **Cache**:
     - Quản lý trong `Cache`.
   - **Đa luồng**:
     - `SyncDAO/Utils` với cơ chế callback trong đa luồng.



---

## Tài liệu liên quan

- **API Documentation**: Chi tiết các API được sử dụng trong dự án có tại thư mục `utils/API`.
- **Thiết kế cơ sở dữ liệu**: File thiết kế cấu trúc database và các bảng có trong [`link-database`](https://dbdiagram.io/d/OOP-library-database-674d2489e9daa85aca4f01a7).
- **Kiểm thử**: Các test case chi tiết được viết bằng JUnit nằm trong thư mục `test`.
  
---

## Liên hệ

Nếu bạn có bất kỳ câu hỏi nào liên quan đến dự án, vui lòng liên hệ với chúng tôi:

- **Nguyễn Hồng Anh**: [honganhnguyenpro655@gmail.com](mailto:honganhnguyenpro655@gmail.com)
- **Nguyễn Đức Dũng**: [dungcuong0507@gmail.com](mailto:dungcuong0507@gmail.com)
- **Trần Ánh Duy**: [duytran2552005@gmail.com](mailto:duytran2552005@gmail.com)
- **Đậu Đức Hiếu**: [dauduchieu.vn@gmail.com](mailto:dauduchieu.vn@gmail.com)

---

## License

Dự án này được phát hành dưới giấy phép **MIT License**.


