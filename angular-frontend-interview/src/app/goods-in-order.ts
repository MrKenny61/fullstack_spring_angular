export class GoodsInOrder {
    constructor(public name?: string, public count?: number, public id?: number, public price?: number, public cost?: number, public deleted?: boolean) {
        this.deleted = false;
    }
}
