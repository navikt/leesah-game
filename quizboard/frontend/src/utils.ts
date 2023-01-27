export function specialChar(string: string | { label: string }) {
  return string.toString().toLowerCase().split('æ').join('ae').split('ø').join('o').split('å').join('a');
}

export function kebabCase(string: string | { label: string }) {
  return specialChar(string).replace(/\s+/g, '-');
}
