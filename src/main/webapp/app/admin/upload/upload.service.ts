import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class JhiUploadService {
    private resourceUrl = SERVER_API_URL + 'api/upload';

    constructor(private http: HttpClient) {}

    sendFile(file: File) {
        return this.http.post<File>(this.resourceUrl, file, { observe: 'response' });
    }
}
