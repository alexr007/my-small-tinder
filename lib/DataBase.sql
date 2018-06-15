PGDMP     1                    v           my-small-tinder    10.4    10.4     #           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            $           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            %           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            &           1262    16448    my-small-tinder    DATABASE     �   CREATE DATABASE "my-small-tinder" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'ru_UA.UTF-8' LC_CTYPE = 'ru_UA.UTF-8';
 !   DROP DATABASE "my-small-tinder";
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            '           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12961    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            (           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    16457    liked    TABLE        CREATE TABLE public.liked (
    "time" bigint NOT NULL,
    user_who_id integer NOT NULL,
    user_whom_id integer NOT NULL
);
    DROP TABLE public.liked;
       public         postgres    false    3            �            1259    16472    messages    TABLE     �   CREATE TABLE public.messages (
    "time" bigint NOT NULL,
    sender integer NOT NULL,
    recipient integer NOT NULL,
    text character varying NOT NULL
);
    DROP TABLE public.messages;
       public         postgres    false    3            �            1259    16449    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    "imgURL" character varying NOT NULL
);
    DROP TABLE public.users;
       public         postgres    false    3                      0    16457    liked 
   TABLE DATA               B   COPY public.liked ("time", user_who_id, user_whom_id) FROM stdin;
    public       postgres    false    197   �                  0    16472    messages 
   TABLE DATA               C   COPY public.messages ("time", sender, recipient, text) FROM stdin;
    public       postgres    false    198   �                 0    16449    users 
   TABLE DATA               3   COPY public.users (id, name, "imgURL") FROM stdin;
    public       postgres    false    196   �       �
           2606    16461    liked liked_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.liked
    ADD CONSTRAINT liked_pkey PRIMARY KEY ("time");
 :   ALTER TABLE ONLY public.liked DROP CONSTRAINT liked_pkey;
       public         postgres    false    197            �
           2606    16479    messages time 
   CONSTRAINT     Q   ALTER TABLE ONLY public.messages
    ADD CONSTRAINT "time" PRIMARY KEY ("time");
 9   ALTER TABLE ONLY public.messages DROP CONSTRAINT "time";
       public         postgres    false    198            �
           2606    16456    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public         postgres    false    196            �
           2606    16485    messages recipient    FK CONSTRAINT     s   ALTER TABLE ONLY public.messages
    ADD CONSTRAINT recipient FOREIGN KEY (recipient) REFERENCES public.users(id);
 <   ALTER TABLE ONLY public.messages DROP CONSTRAINT recipient;
       public       postgres    false    198    196    2716            �
           2606    16480    messages sender    FK CONSTRAINT     m   ALTER TABLE ONLY public.messages
    ADD CONSTRAINT sender FOREIGN KEY (sender) REFERENCES public.users(id);
 9   ALTER TABLE ONLY public.messages DROP CONSTRAINT sender;
       public       postgres    false    198    196    2716            �
           2606    16462    liked user_who_id    FK CONSTRAINT     t   ALTER TABLE ONLY public.liked
    ADD CONSTRAINT user_who_id FOREIGN KEY (user_who_id) REFERENCES public.users(id);
 ;   ALTER TABLE ONLY public.liked DROP CONSTRAINT user_who_id;
       public       postgres    false    2716    196    197            �
           2606    16467    liked user_whom_id    FK CONSTRAINT     v   ALTER TABLE ONLY public.liked
    ADD CONSTRAINT user_whom_id FOREIGN KEY (user_whom_id) REFERENCES public.users(id);
 <   ALTER TABLE ONLY public.liked DROP CONSTRAINT user_whom_id;
       public       postgres    false    197    2716    196                  x������ � �             x������ � �            x������ � �     