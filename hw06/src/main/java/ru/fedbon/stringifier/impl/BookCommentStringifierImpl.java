package ru.fedbon.stringifier.impl;

import org.springframework.stereotype.Component;
import ru.fedbon.model.BookComment;
import ru.fedbon.stringifier.BookCommentStringifier;


@Component
public class BookCommentStringifierImpl implements BookCommentStringifier {

    @Override
    public String stringify(BookComment bookComment) {
        return "Comment(id=" + bookComment.getId() + ", commentText=" + bookComment.getText() + ")";
    }
}
