package com.kh.youtube.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideoLike is a Querydsl query type for VideoLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideoLike extends EntityPathBase<VideoLike> {

    private static final long serialVersionUID = 1017691607L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideoLike videoLike = new QVideoLike("videoLike");

    public final QMember member;

    public final QVideo video;

    public final NumberPath<Integer> vLikeCode = createNumber("vLikeCode", Integer.class);

    public final DateTimePath<java.util.Date> vLikeDate = createDateTime("vLikeDate", java.util.Date.class);

    public QVideoLike(String variable) {
        this(VideoLike.class, forVariable(variable), INITS);
    }

    public QVideoLike(Path<? extends VideoLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideoLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideoLike(PathMetadata metadata, PathInits inits) {
        this(VideoLike.class, metadata, inits);
    }

    public QVideoLike(Class<? extends VideoLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.video = inits.isInitialized("video") ? new QVideo(forProperty("video"), inits.get("video")) : null;
    }

}

