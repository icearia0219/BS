

public class Contact {
    private String id;   // 唯一标识符
    private String name; // 联系人姓名
    private String phone; // 联系人电话

    public Contact(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    // 获取 ID
    public String getId() {
        return id;
    }

    // 获取姓名
    public String getName() {
        return name;
    }

    // 设置姓名
    public void setName(String name) {
        this.name = name;
    }

    // 获取电话
    public String getPhone() {
        return phone;
    }

    // 设置电话
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
