export class ActivityAlreadyBooked extends Error {
    constructor(nameActivity: string, namePartner: string, date: string) {
        super(`The activity "${nameActivity}" with the partner "${namePartner}" on the date "${date}" has already been booked`);
    }
}
