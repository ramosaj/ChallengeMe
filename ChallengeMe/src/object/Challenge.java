public class Challenge {
    private int id;
    private User owner;
    private String name;
    private String description;
    private Date createAt;
    private Date updateAt;
    private Map<Integer, String> comments;  //Key: User ID Value: comments (String)

    public Challenge(int id, User owner, String name, String description, Date createAt) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.createAt = createAt;
        this.updateAt = this.createAt;
        this.comments = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Map<Integer, String> getComments() {
        return comments;
    }

    public void setComments(Map<Integer, String> comments) {
        this.comments = comments;
    }
}