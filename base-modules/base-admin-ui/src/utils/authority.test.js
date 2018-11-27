import { getAuthority } from './authority';

describe('getAuthority should be strong', () => {
  it('empty', () => {
    expect(getAuthority(null)).toEqual(['upms']); // default value
  });
  it('string', () => {
    expect(getAuthority('upms')).toEqual(['upms']);
  });
  it('array with double quotes', () => {
    expect(getAuthority('"upms"')).toEqual(['upms']);
  });
  it('array with single item', () => {
    expect(getAuthority('["upms"]')).toEqual(['upms']);
  });
  it('array with multiple items', () => {
    expect(getAuthority('["upms", "guest"]')).toEqual(['upms', 'guest']);
  });
});
