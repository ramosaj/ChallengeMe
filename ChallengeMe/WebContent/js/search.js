$('#searchForm').submit(function (event) {
    event.preventDefault();
    var searchedItem = $("#searchItem").val().trim();
    $.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/SearchTopic",
        data: {
            searchItem : searchItem
        },
        success: function(data){
            if(data.length == 0) {
                alert("Sorry, the topic: " + $("#topicSearch").val() + " doesn't exist!");
            } else {
                for(var index in data) {
                    $("#" + data[index]).css("background-color", "red");
                }
            }
        }
    });
});