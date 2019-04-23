import { TestBed } from '@angular/core/testing';

import { SearchCVService } from './search-cv.service';

describe('SearchCVService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SearchCVService = TestBed.get(SearchCVService);
    expect(service).toBeTruthy();
  });
});
