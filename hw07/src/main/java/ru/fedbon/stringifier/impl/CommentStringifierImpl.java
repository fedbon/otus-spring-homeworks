package ru.fedbon.stringifier.impl;

import org.springframework.stereotype.Component;
import ru.fedbon.model.Comment;
import ru.fedbon.stringifier.CommentStringifier;


@Component
public class CommentStringifierImpl implements CommentStringifier {

    @Override
    public String stringify(Comment comment) {
        return "Comment(id=" + comment.getId() + ", commentText=" + comment.getText() + ")";
    }
}
