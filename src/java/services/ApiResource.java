/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
    @Path("/bands/{objectId}")
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
    @Path("/addBand/{bandName}/{url}/{bio}")
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
        return "posted";
    }
    
    /////////////////////////////////////////ALBUM /////////////////////////////////////////
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
