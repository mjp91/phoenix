import moment from 'moment';

export default (holiday) => {
  const holidayDates = holiday.holidayDates ? holiday.holidayDates : [];

  if (holidayDates.length === 0) {
    return null;
  }

  const firstDate = holidayDates[0];

  return moment(firstDate.date).fromNow();
};