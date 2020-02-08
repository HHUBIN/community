function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}
function  comment2target(targetId,type,content) {

    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if(response.code == 200){
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=6b3ab7b9929bd54b9110&redirect_uri=http://localhost/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);
                    }
                }
            }
        },
        dataType:"json"
    });
}
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId, 2, content);

}

function collapseComments(e) {

    $(e).toggleClass("comment-icon")
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
/*    if(comments.hasClass("in")){

    }*/
    comments.toggleClass("in");
    if(comments.hasClass("in")){
        var subCommentContaioner = $("#comment-"+id);

        if(subCommentContaioner.children().length == 1){
            $.getJSON("/comment/" + id, function (data) {

                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "name",
                        "html": comment.user.name
                    })).append($("<h5/>", {
                        "html": comment.content
                    })).append($("<span/>", {
                        "class": "glyphicon glyphicon-thumbs-up comment-color"
                    })/*.append($("<span/>", {
                        "class": "comment-time comment-color",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    }))*/);

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement).append($("<hr>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-hr"
                    }));

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12"
                    }).append(mediaElement);

                    subCommentContaioner.prepend(commentElement);
                });

            });

        }
    }
}