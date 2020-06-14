/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import artist.Album;
import artist.Artist;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import com.mongodb.util.JSON;
import javax.ws.rs.DELETE;

/**
 * REST Web Service
 *
 * @author tomaszkoltun
 */
@Path("api")
public class ApiResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ApiResource
     */
    public ApiResource() {
    }

    /**
     * Retrieves representation of an instance of services.ApiResource
     * @return an instance of java.lang.String
     */
    
    
    // get all bands
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllArtists() throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject searchQuery = new BasicDBObject();
        DBCursor cursor = collection.find();
        String s = new String();
        while (cursor.hasNext()) {
            s+=cursor.next();
        }
        JSONObject jsonObject = new JSONObject(s);
        return s;
    }
    
    ////////////////////////////////////////////// BAND /////////////////////////////////////////////////////
    @GET
    @Path("/artists/{objectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBandFromId(@PathParam("objectId") String objectId) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        ObjectId id = new ObjectId(objectId);
        DBCollection collection = database.getCollection("artists");
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", id);
        DBCursor cursor = collection.find(searchQuery);
        String s = new String();
        while (cursor.hasNext()) {
            s+=cursor.next();
        }
        System.out.println(s);
//        JSONObject jsonObject = new JSONObject(s);
        return s;
    }
    
    @POST
    @Path("/addArtist/{bandName}/{url}/{bio}")
    @Produces(MediaType.APPLICATION_JSON)
    public String postBand(@PathParam("bandName") String bandName, @PathParam("url") String url, @PathParam("bio") String bio) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject document = new BasicDBObject();
        document.put("nazwa", bandName);
        document.put("bio", bio);
        document.put("imageUrl", url);
        collection.insert(document);
        return "added";
    }
    
    @POST
    @Path("/addArtistWithBody")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String postArtist(Artist a) throws UnknownHostException{
        System.out.println(a.toString());
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject document = new BasicDBObject();
        document.put("nazwa", a.getNazwa());
        document.put("bio", a.getBio());
        document.put("imageUrl", a.getImageUrl());
        collection.insert(document);
        return a.toString();
    }
    
    
    @POST
    @Path("/addAlbumWithBody")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String postAlbum(Album a) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(a.getArtistId());
        whereQuery.put("_id",id);
        ObjectId id2= new ObjectId();
        String json = 
"	{$push: {\"albums\" : {_id:\""+id2+"\",label:\""+a.getName()+"\", year:"+a.getYear()+", imageUrl: \""+a.getLabelUrl()+"\"}\n" +
"	}}";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return a.getName();
    }
    
    /////////////////////////////////////////ALBUM /////////////////////////////////////////
    //wyswietlenie nazw albumow i piosenek w nich
    @GET
    @Path("/getAlbums/{bandId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAlbumsFromBand(@PathParam("bandId")String bandId) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(bandId);
        whereQuery.put("_id",id);
        BasicDBObject fields = new BasicDBObject();
        fields.put("albums.label", 1);
        fields.put("albums.songs.nazwa", 1);
        DBCursor cursor = collection.find(whereQuery, fields);
        String s = new String();
        while (cursor.hasNext()) {
            s+=cursor.next();
        }
        System.out.println(s);
//        JSONObject jsonObject = new JSONObject(s);
        return s;
    }
    @GET
    @Path("/getSongsFromAlbum/{albumId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSongFromAlbumId(@PathParam("albumId")String albumId) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(albumId);
        whereQuery.put("albums._id",albumId);
        BasicDBObject fields = new BasicDBObject();
        fields.put("albums.songs.nazwa", 1);
        DBCursor cursor = collection.find(whereQuery, fields);
        String s = new String();
        while (cursor.hasNext()) {
            s+=cursor.next();
        }
        System.out.println(s);
//        JSONObject jsonObject = new JSONObject(s);
        return s;
    }
    
    @POST
    @Path("/addAlbum/{bandId}/{label}/{year}/{url}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addAlbumToBand(@PathParam("bandId") String bandId, @PathParam("label") String label,@PathParam("year") int year,@PathParam("url") String url ) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(bandId);
        whereQuery.put("_id",id);
        ObjectId id2= new ObjectId();
        String json = 
"	{$push: {\"albums\" : {_id:\""+id2+"\",label:\""+label+"\", year:"+year+", imageUrl: \""+url+"\"}\n" +
"	}}";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return "added";
    }
    
    @POST
    @Path("/addSong/{bandId}/{albumId}/{nazwa}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addSongToAlbum(@PathParam("bandId") String bandId, @PathParam("albumId") String albumId,@PathParam("nazwa") String nazwa) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(bandId);
        ObjectId album = new ObjectId(albumId);
        whereQuery.put("_id",id);
        whereQuery.put("albums._id", album.toString());
        ObjectId id2= new ObjectId();
        String json = 
"	{$push: {\"albums.$.songs\" : {\"_id\":\""+id2+"\",\"nazwa\":\""+nazwa+"\"}}}";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return album.toString();
    }
    
    @POST
    @Path("/addComment/{albumId}/{songNo}/{name}/{comment}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addComment(@PathParam("albumId") String albumId, @PathParam("songNo") String songNo ,@PathParam("name") String name, @PathParam("comment") String comment) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(albumId);
        ObjectId commentId = new ObjectId();
        whereQuery.put("albums._id",albumId);
        String json = " 	{ \"$push\": { \"albums.$.songs."+songNo+".Comments\": {\"_id\":\""+commentId+"\",name: \""+name+"\", comment:\""+comment+"\"} } }";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return "added";
    }
    
    @POST
    @Path("/addComment2/{albumId}/{songId}/{name}/{comment}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addComment2(@PathParam("albumId") String albumId, @PathParam("songId") String songId ,@PathParam("name") String name, @PathParam("comment") String comment) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(albumId);
        ObjectId commentId = new ObjectId();
        whereQuery.put("albums._id",albumId);
        DBCursor cursor = collection.find(whereQuery);
        String s = new String();
        while (cursor.hasNext()) {
            s+=cursor.next();
        }
        System.out.println(s);
        int songNo=0; //sprawdza numer piosenki w albumie
        String[] words = s.split(" ");
        for(int i=0;i<words.length;i++){
            if(words[i].contains("Comments"))
                songNo++;
            if(words[i].contains(songId))
                break;
        }
        String json = " 	{ \"$push\": { \"albums.$.songs."+songNo+".Comments\": {\"_id\":\""+commentId+"\",name: \""+name+"\", comment:\""+comment+"\"} } }";
//        String json = " ";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return "added comment to song no"+songNo;
    }
    
    @POST
    @Path("/addRating/{albumId}/{songNo}/{rating}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addRating(@PathParam("albumId") String albumId, @PathParam("songNo") String songNo ,@PathParam("rating") String rating) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(albumId);
        ObjectId commentId = new ObjectId();
        whereQuery.put("albums._id",albumId);
        String json = " 	{ \"$push\": { \"albums.$.songs."+songNo+".ratings\":"+rating+"    } }";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return "added";
    }
    
    
    @POST
    @Path("/addRating2/{albumId}/{songId}/{rating}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addRating2(@PathParam("albumId") String albumId, @PathParam("songId") String songId , @PathParam("rating") int rating) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(albumId);
        ObjectId commentId = new ObjectId();
        whereQuery.put("albums._id",albumId);
        DBCursor cursor = collection.find(whereQuery);
        String s = new String();
        while (cursor.hasNext()) {
            s+=cursor.next();
        }
        System.out.println(s);
        int songNo=0; //sprawdza numer piosenki w albumie
        String[] words = s.split(" ");
        for(int i=0;i<words.length;i++){
            if(words[i].contains("ratings"))
                songNo++;
            if(words[i].contains(songId))
                break;
        }
        String json = " 	{ \"$push\": { \"albums.$.songs."+songNo+".ratings\":"+rating+"} } }";
//        String json = " ";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return "added rating to song no"+songNo;
    }
    
    @PUT
    @Path("editBio/{bandId}/{newBio}")
    @Produces(MediaType.APPLICATION_JSON)
    public String editBio(@PathParam("bandId") String bandId, @PathParam("newBio") String bio) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(bandId);
        whereQuery.put("_id",id);
        String json="{$set: {bio: \""+bio+"\"}}";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return "modified bio";
    }
    
    @PUT
    @Path("editArtistImage/{bandId}/{newImage}")
    @Produces(MediaType.APPLICATION_JSON)
    public String editArtistImage(@PathParam("bandId") String bandId, @PathParam("newImage") String image) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(bandId);
        whereQuery.put("_id",id);
        String json="{$set: {imageUrl: \""+image+"\"}}";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return "modified image";
    }
    
    @PUT
    @Path("editArtistName/{bandId}/{newName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String editArtistName(@PathParam("bandId") String bandId, @PathParam("newName") String name) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(bandId);
        whereQuery.put("_id",id);
        String json="{$set: {nazwa: \""+name+"\"}}";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        return "modified name";
    }
    
    @DELETE
    @Path("removeArtist/{bandId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteArtist(@PathParam("bandId") String bandId) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(bandId);
        whereQuery.put("_id",id);
        collection.remove(whereQuery);
        
        return "removed";
    }
    
    @DELETE
    @Path("removeAlbum/{albumId}")
    @Produces(MediaType.APPLICATION_JSON) //rozpierdala cala baze, jeszcze nie wiem dlaczego
    public String deleteAlbum(@PathParam("albumId") String albumId) throws UnknownHostException{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("artists");
        boolean auth = database.authenticate("admin", "admin".toCharArray());
        DBCollection collection = database.getCollection("artists");
        BasicDBObject whereQuery = new BasicDBObject();
        ObjectId id = new ObjectId(albumId);
        whereQuery.put("albums._id",id);
        DBCursor cursor = collection.find(whereQuery);
        String s = new String();
        while (cursor.hasNext()) {
            s+=cursor.next();
        }
        int labelCount=0; //sprawdza numer albumu u artysty
        String[] words = s.split(" ");
        for(int i=0;i<words.length;i++){
            if(words[i].contains("label"))
                labelCount++;
            if(words[i].contains(albumId))
                break;
        }
        System.out.println(s);
//        String json = " 	{ \"$push\": { \"albums.$.songs."+songNo+".Comments\": {\"_id\":\""+commentId+"\",name: \""+name+"\", comment:\""+comment+"\"} } }";
        String json = " ";
        DBObject push = (DBObject) JSON.parse(json);
        collection.update(whereQuery,push);
        
        return "removed";
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ApiResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
