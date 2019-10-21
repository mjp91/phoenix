export default (holiday) => {
  let holidayDates = holiday.holidayDates ? holiday.holidayDates : [];

  let firstDate, lastDate, length = 0.0;
  for (let holidayDate of holidayDates) {
    if (holidayDate.holidayPeriod === 'ALL_DAY') {
      length += 1.0;
    } else {
      length += 0.5;
    }

    const thisDate = new Date(holidayDate.date);
    if (!firstDate || thisDate < firstDate) {
      firstDate = thisDate;
    }

    if (!lastDate || thisDate > lastDate) {
      lastDate = thisDate;
    }
  }

  let unit = "days";

  if (length === 1) {
    unit = "day";
  }

  return {
    range: firstDate ? `${firstDate.toLocaleDateString()} - ${lastDate.toLocaleDateString()}` : null,
    length: `${length} ${unit}`
  };
};
