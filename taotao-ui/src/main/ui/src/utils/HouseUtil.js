export default {
  methods: {
    toApartment(str) {
      if (str === 'yi') {
        return '一室一厅'
      } else if (str === 'er') {
        return '两室一厅'
      } else if (str === 'san') {
        return '三室一厅'
      } else if (str === 'si') {
        return '四室一厅'
      } else if (str === 'wu') {
        return '五室一厅'
      } else if (str === 'liu') {
        return '六室一厅'
      } else if (str === 'qi') {
        return '七室一厅'
      }
    }
  }
}
