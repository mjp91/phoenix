export default (dateStr) => {
  return new Date(dateStr).toLocaleString();
};

export const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString();
};
