package com.kh.youtube.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChannel is a Querydsl query type for Channel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChannel extends EntityPathBase<Channel> {

    private static final long serialVersionUID = -2077016728L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChannel channel = new QChannel("channel");

    public final NumberPath<Integer> channelCode = createNumber("channelCode", Integer.class);

    public final DateTimePath<java.util.Date> channelDate = createDateTime("channelDate", java.util.Date.class);

    public final StringPath channelDesc = createString("channelDesc");

    public final StringPath channelName = createString("channelName");

    public final StringPath channelPhoto = createString("channelPhoto");

    public final QMember member;

    public final QVideo uploadPath;

    public QChannel(String variable) {
        this(Channel.class, forVariable(variable), INITS);
    }

    public QChannel(Path<? extends Channel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChannel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChannel(PathMetadata metadata, PathInits inits) {
        this(Channel.class, metadata, inits);
    }

    public QChannel(Class<? extends Channel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.uploadPath = inits.isInitialized("uploadPath") ? new QVideo(forProperty("uploadPath"), inits.get("uploadPath")) : null;
    }

}

