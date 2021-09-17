export class GoodsList {
    constructor(public id?: number, public name?: string, public count?: number, public price?: number, public cost?: number, public deleted?: boolean) {
        this.deleted = false;
    }
}
