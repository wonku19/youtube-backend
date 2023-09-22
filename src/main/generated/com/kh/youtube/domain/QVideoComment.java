package com.kh.youtube.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideoComment is a Querydsl query type for VideoComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideoComment extends EntityPathBase<VideoComment> {

    private static final long serialVersionUID = 652883999L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideoComment videoComment = new QVideoComment("videoComment");

    public final NumberPath<Integer> commentCode = createNumber("commentCode", Integer.class);

    public final DateTimePath<java.util.Date> commentDate = createDateTime("commentDate", java.util.Date.class);

    public final StringPath commentDesc = createString("commentDesc");

    public final NumberPath<Integer> commentParent = createNumber("commentParent", Integer.class);

    public final QMember member;

    public final QVideo video;

    public QVideoComment(String variable) {
        this(VideoComment.class, forVariable(variable), INITS);
    }

    public QVideoComment(Path<? extends VideoComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideoComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideoComment(PathMetadata metadata, PathInits inits) {
        this(VideoComment.class, metadata, inits);
    }

    public QVideoComment(Class<? extends VideoComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.video = inits.isInitialized("video") ? new QVideo(forProperty("video"), inits.get("video")) : null;
    }

}

