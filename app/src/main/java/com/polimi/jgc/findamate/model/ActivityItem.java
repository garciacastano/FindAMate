package com.polimi.jgc.findamate.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class ActivityItem
{
  private java.util.Date updated;
  private Integer id;
  private java.util.Date date_;
  private String category;
  private String objectId;
  private String ownerId;
  private String latitude;
  private Integer participants;
  private Integer assistants;
  private java.util.Date created;
  private String title;
  private Double longitude;
  private String description;
  public java.util.Date getUpdated()
  {
    return updated;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId( Integer id )
  {
    this.id = id;
  }

  public java.util.Date getDate_()
  {
    return date_;
  }

  public void setDate_( java.util.Date date_ )
  {
    this.date_ = date_;
  }

  public String getCategory()
  {
    return category;
  }

  public void setCategory( String category )
  {
    this.category = category;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getLatitude()
  {
    return latitude;
  }

  public void setLatitude( String latitude )
  {
    this.latitude = latitude;
  }

  public Integer getParticipants()
  {
    return participants;
  }

  public void setParticipants( Integer participants )
  {
    this.participants = participants;
  }

  public Integer getAssistants()
  {
    return assistants;
  }

  public void setAssistants( Integer assistants )
  {
    this.assistants = assistants;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  public Double getLongitude()
  {
    return longitude;
  }

  public void setLongitude( Double longitude )
  {
    this.longitude = longitude;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription( String description )
  {
    this.description = description;
  }

                                                    
  public ActivityItem save()
  {
    return Backendless.Data.of( ActivityItem.class ).save( this );
  }

  public Future<ActivityItem> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ActivityItem> future = new Future<ActivityItem>();
      Backendless.Data.of( ActivityItem.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<ActivityItem> callback )
  {
    Backendless.Data.of( ActivityItem.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( ActivityItem.class ).remove( this );
  }

  public Future<Long> removeAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Long> future = new Future<Long>();
      Backendless.Data.of( ActivityItem.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( ActivityItem.class ).remove( this, callback );
  }

  public static ActivityItem findById( String id )
  {
    return Backendless.Data.of( ActivityItem.class ).findById( id );
  }

  public static Future<ActivityItem> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ActivityItem> future = new Future<ActivityItem>();
      Backendless.Data.of( ActivityItem.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<ActivityItem> callback )
  {
    Backendless.Data.of( ActivityItem.class ).findById( id, callback );
  }

  public static ActivityItem findFirst()
  {
    return Backendless.Data.of( ActivityItem.class ).findFirst();
  }

  public static Future<ActivityItem> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ActivityItem> future = new Future<ActivityItem>();
      Backendless.Data.of( ActivityItem.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<ActivityItem> callback )
  {
    Backendless.Data.of( ActivityItem.class ).findFirst( callback );
  }

  public static ActivityItem findLast()
  {
    return Backendless.Data.of( ActivityItem.class ).findLast();
  }

  public static Future<ActivityItem> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ActivityItem> future = new Future<ActivityItem>();
      Backendless.Data.of( ActivityItem.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<ActivityItem> callback )
  {
    Backendless.Data.of( ActivityItem.class ).findLast( callback );
  }

  public static BackendlessCollection<ActivityItem> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( ActivityItem.class ).find( query );
  }

  public static Future<BackendlessCollection<ActivityItem>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<ActivityItem>> future = new Future<BackendlessCollection<ActivityItem>>();
      Backendless.Data.of( ActivityItem.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<ActivityItem>> callback )
  {
    Backendless.Data.of( ActivityItem.class ).find( query, callback );
  }
}