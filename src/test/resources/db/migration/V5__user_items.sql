CREATE table user_to_item
(
    id      serial primary key,
    user_id bigint references users (id),
    item_id bigint references items (id)
);
