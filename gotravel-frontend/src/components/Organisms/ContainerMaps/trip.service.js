import Axios from 'axios';

class TripService {
  constructor(trips, jwtkey) {
    this.trips = trips;
    this.jwtkey = jwtkey;
  }

  async createTrip() {
    console.log(this.trips);
    return Axios.post('/trip/' + this.trips.idUser, this.trips, { headers: { authorization: this.jwtkey } });
  }

  async createFilter(tripId, filter) {
    return Axios.post('/filter/' + tripId, filter, { headers: { authorization: this.jwtkey } });
  }
}

export default TripService;
