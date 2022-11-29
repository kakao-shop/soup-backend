insert ignore into member (member_idx, id, password, birthday, gender, last_access_time, nickname, oauth, role,total_access_cnt, created_at, updated_at)
values (1, 'admin', '$2a$10$bA41MsmjYOtAvNQWU.juzuZuZltAfBW5qiHZDbZdtWV0Fr2GlK4/S', now(), 'M', now(), '관리자', 'ORIGIN', 'ADMIN', 0, now(), now());

insert ignore into theme (idx, created_at, title)
values (1, now(), '집에서도 간편하게! 밀키트 모음전 🍜');

insert ignore into theme_category (idx, main_category, sub_category, theme_idx)
values (1, '냉장/냉동식품', '밀키트', 1);

insert ignore into banner (idx, content_type, path, size, title, theme_idx)
values ('55e79fb9-5678-42d9-b5cd-8d14c924a794', 'image/png',
        883424, '밀키트.png', 1);