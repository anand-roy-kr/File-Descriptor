public class Consumer1 {
    private String name;
    private Integer order;
    private Integer min_width;
    private Integer max_width;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getMin_width() {
        return min_width;
    }

    public void setMin_width(Integer min_width) {
        this.min_width = min_width;
    }

    public Integer getMax_width() {
        return max_width;
    }

    public void setMax_width(Integer max_width) {
        this.max_width = max_width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Consumer1{" +
                "name='" + name + '\'' +
                ", order=" + order +
                ", min_width=" + min_width +
                ", max_width=" + max_width +
                ", type='" + type + '\'' +
                '}';
    }
}
