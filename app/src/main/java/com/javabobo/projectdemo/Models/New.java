package com.javabobo.projectdemo.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luis on 14/02/2018.
 */
@Root(name = "rss", strict = false) //root of the xml file
public class New {
    @Element
    private Channel channel;

    @Root(name = "channel", strict = false)
    public static class Channel {
        //  @ElementList(name = "item")
        //    private List<item> items;
        //;

        @Element(name = "title", type = String.class)
        private static String title;

        @ElementList(entry = "item", inline = true)
        private List<Item> items;

        public static String getTitle() {
            return title;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    @Root(name = "item", strict = false)
    public static class Item implements Serializable {
        @Element(name = "title", type = String.class)
        private String title;
        @Element(name = "description", type = String.class)
        private String description;
        @Element(name = "link", type = String.class)
        private String link;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getLink() {
            return link;
        }

        public Img getImg() {
            return img;
        }

        @Element(name = "thumbnail")
        private Img img;


        @Root(name = "thumbnail", strict = false)
       public static class Img implements Serializable {
            @Attribute(name = "url")
            private String url;

            public String getUrl() {
                return url;
            }
        }
    }

    public Channel getChannel() {
        return channel;
    }

}
