package com.kh.youtube.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideo is a Querydsl query type for Video
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideo extends EntityPathBase<Video> {

    private static final long serialVersionUID = 1646701088L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideo video = new QVideo("video");

    public final QCategory category;

    public final QChannel channel;

    public final QMember member;

    public final NumberPath<Integer> videoCode = createNumber("videoCode", Integer.class);

    public final DateTimePath<java.util.Date> videoDate = createDateTime("videoDate", java.util.Date.class);

    public final StringPath videoDesc = createString("videoDesc");

    public final StringPath videoPhoto = createString("videoPhoto");

    public final StringPath videoTitle = createString("videoTitle");

    public final StringPath videoUrl = createString("videoUrl");

    public final NumberPath<Integer> videoViews = createNumber("videoViews", Integer.class);

    public QVideo(String variable) {
        this(Video.class, forVariable(variable), INITS);
    }

    public QVideo(Path<? extends Video> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideo(PathMetadata metadata, PathInits inits) {
        this(Video.class, metadata, inits);
    }

    public QVideo(Class<? extends Video> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.channel = inits.isInitialized("channel") ? new QChannel(forProperty("channel"), inits.get("channel")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}
