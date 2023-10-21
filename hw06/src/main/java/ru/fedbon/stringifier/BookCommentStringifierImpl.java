package ru.fedbon.stringifier;

import org.springframework.stereotype.Component;
import ru.fedbon.model.BookComment;


@Component
public class BookCommentStringifierImpl implements BookCommentStringifier {

    @Override
    public String stringify(BookComment bookComment) {
        return "Comment(id=" + bookComment.getId() + ", commentText=" + bookComment.getText() + ")";
    }
}
