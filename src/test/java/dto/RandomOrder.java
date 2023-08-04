package dto;

public class RandomOrder {
    String status;
    Integer courierId;
    String customerName;
    String customerPhone;
    String comment;
    int id;

    public RandomOrder() {
    }

    public String getStatus() {
        return status;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}