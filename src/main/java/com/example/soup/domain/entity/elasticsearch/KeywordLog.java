package com.example.soup.domain.entity.elasticsearch;

import com.example.soup.domain.entity.BaseCreatedTimeEntity;
import com.example.soup.domain.entity.mariadb.Member;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

@Document(indexName = "keyword_logs")
@Entity
public class KeywordLog extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    private String keyword;

    public void setMember(Member member) {
        this.member = member;
    }

}
