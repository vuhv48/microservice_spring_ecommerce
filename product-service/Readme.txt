Bước 1: Tạo Pluggable Database (PDB) mới
Oracle 19c sử dụng kiến trúc đa pluggable (CDB + PDB). Để tạo service name cho database mới, bạn cần tạo một pluggable database (PDB) bên trong container database (CDB).

Kết nối vào Oracle như SYSDBA:


sqlplus sys/mypassword1@localhost:1521/ORCLCDB AS SYSDBA
Tạo Pluggable Database mới (PDB): Giả sử bạn muốn tạo một PDB mới có tên là product:


CREATE PLUGGABLE DATABASE product
ADMIN USER admin IDENTIFIED BY admin_password
ROLES = (DBA)
DEFAULT TABLESPACE users
DATAFILE '/opt/oracle/oradata/ORCLCDB/product01.dbf' SIZE 500M AUTOEXTEND ON
FILE_NAME_CONVERT = ('/opt/oracle/oradata/ORCLCDB/', '/opt/oracle/oradata/ORCLCDB/product/');
Lệnh trên tạo một pluggable database (PDB) có tên product với các tham số sau:

ADMIN USER: Tạo người dùng admin cho PDB và đặt mật khẩu là admin_password.
DATAFILE: Đặt một tệp dữ liệu cho PDB.
FILE_NAME_CONVERT: Thực hiện chuyển đổi đường dẫn tệp dữ liệu cho phù hợp với PDB mới.
Bước 2: Mở Pluggable Database product
Sau khi tạo PDB, bạn cần mở PDB để có thể sử dụng nó:

ALTER PLUGGABLE DATABASE product OPEN;

Bước 3: Kiểm tra PDB mới
Để kiểm tra nếu PDB product đã được mở thành công, bạn có thể chạy lệnh sau:

SHOW CON_NAME;
Lệnh này sẽ hiển thị tên của PDB hiện tại, ví dụ: product.

Bước 4: Tạo Service Name cho PDB product
Khi bạn tạo một PDB, Oracle sẽ tự động tạo một service name cho PDB đó. Tuy nhiên, nếu bạn muốn chỉ định hoặc thay đổi service name cho PDB, bạn có thể làm như sau:

Kết nối vào container database (CDB) như SYSDBA:

sqlplus sys/mypassword1@localhost:1521/ORCLCDB AS SYSDBA
Tạo hoặc thay đổi service name cho PDB product: Bạn có thể thêm một service name cho PDB product bằng cách sử dụng câu lệnh ALTER SYSTEM:

ALTER SYSTEM SET LOCAL_LISTENER='(ADDRESS=(PROTOCOL=tcp)(HOST=localhost)(PORT=1521))';
Đảm bảo listener biết về service name product: Kiểm tra lại listener xem có service name product không:

lsnrctl status
Bạn sẽ thấy các service name của PDBs như orclpdb1, product, v.v...

Bước 5: Kiểm tra và kết nối với PDB product
Cuối cùng, sau khi PDB product đã được mở và có service name, bạn có thể kết nối với PDB này từ ứng dụng Java hoặc từ SQL*Plus:

Kết nối bằng SQL*Plus:
bash
Sao chép mã
sqlplus admin/admin_password@localhost:1521/product
Kết nối từ ứng dụng Spring Boot:
Cập nhật URL trong file application.properties của Spring Boot:

properties
Sao chép mã
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/product
spring.datasource.username=admin
spring.datasource.password=admin_password
Tổng kết các bước
Tạo PDB: Tạo một pluggable database mới bằng câu lệnh CREATE PLUGGABLE DATABASE.
Mở PDB: Mở PDB bằng ALTER PLUGGABLE DATABASE product OPEN;.
Kiểm tra Listener: Đảm bảo rằng service name của PDB đã được listener nhận diện.
Kết nối: Kết nối đến PDB mới qua SQL*Plus hoặc ứng dụng.
Nếu bạn gặp lỗi trong quá trình này, hãy kiểm tra lại các quyền của người dùng sys hoặc admin trong database và đảm bảo rằng các tệp dữ liệu được chỉ định đúng.
3. Cấp quyền cho người dùng:
Sau khi tạo người dùng admin, cấp quyền cho người dùng này để có thể sử dụng và tạo các bảng, dữ liệu trong PDB. Ví dụ:

GRANT CONNECT, RESOURCE TO admin;
ALTER USER admin QUOTA UNLIMITED ON USERS;
ALTER USER admin QUOTA 100M ON USERS;


----
CREATE PLUGGABLE DATABASE orders
  ADMIN USER admin IDENTIFIED BY admin_password
  ROLES = (DBA)
  DEFAULT TABLESPACE users
  DATAFILE '/opt/oracle/oradata/ORCLCDB/orders01.dbf' SIZE 500M AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED
  FILE_NAME_CONVERT = ('/opt/oracle/oradata/ORCLCDB/', '/opt/oracle/oradata/ORCLCDB/orders/');

ALTER PLUGGABLE DATABASE orders OPEN;


sqlplus admin/admin_password@//localhost:1521/orders
BEGIN
  FOR t IN (SELECT table_name FROM all_tables WHERE owner = 'ORDERS') LOOP
    EXECUTE IMMEDIATE 'GRANT INSERT, UPDATE, DELETE ON ORDERS.' || t.table_name || ' TO your_user';
  END LOOP;
END;
/
ALTER USER admin QUOTA UNLIMITED ON USERS;
ALTER USER admin QUOTA 100M ON USERS;
GRANT CREATE ANY TABLE TO admin;
GRANT ALTER ANY TABLE TO admin;


