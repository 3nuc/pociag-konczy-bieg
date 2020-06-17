# pociag-konczy-bieg

webresources/api/all  - wyrzyg robi jak pebe po hotdogu z orlena

webresources/api/artists/{objectId} wyszukiwarka wykonawcy po id

webresources/api/getSongsFromAlbum/{albumid} wyszukiwarka albumu po id

webresources/api/addArtist/{bandName}/{url}/{bio} - dodaje wykonawce

webresources/api/getAlbums/{bandId} - wyswietla albumy wykonawce

webresources/api/addAlbum/{bandId}/{label}/{year}/{url} - dodaje album

webresources/api/addSong/{bandId}/{albumId}/{nazwa} - dodaje piosenke do albumu

webresources/api/addComment/{albumId}/{songNo}/{name}/{comment} - dodaje komenta do piosenki po jej kolejnosci w tabeli NIEZALECANE

webresources/api/addComment2/{albumId}/{songId}/{name}/{comment} -dodaje komenta po ID piosenki ZALECANE

webresources/api/addRating/{albumId}/{songNo}/{rating} - podaje rating do piosenki po jej kolejnosci w tabeli NIEZALECANE

webresources/api/addRating2/{albumId}/{songId}/{rating} - dodaje rating do piosenki po ID ZALECANE

webresources/api/editBio/{bandId}/{newBio} - edycja bio zespolu

webresources/api/editArtistImage/{bandId}/{newImage} - edycja url zespolu

webresources/api/editArtistName/{bandId}/{newName} - edycja nazwy

webresources/api/editArtist/{artistid} - edycja artysty

body edycji: {
  "nazwa" : "lorem",
  "imageUrl: "ipsum",
  "bio": "lorem ipsum"
}

webresources/api/addArtistWithBody

body edycji: {
  "nazwa" : "lorem",
  "bio": "lorem ipsum",
   "imageUrl: "ipsum"
}

webresources/api/addAlbumWithBody

body dodania: {
  "artistId" : "id",
  "name": "lorem ipsum",
   "imageUrl: "ipsum"
   "year" : int
}

webresources/api/removeArtist/{idartysty}  - usuwa artyste
webresources/api/removeAlbum/{idartysty}/{idalbumu} - usuwa album
webresources/api/removeSong/{idartysty}/{idalbumu}/{idPiosenki} - usuwa piosenke

